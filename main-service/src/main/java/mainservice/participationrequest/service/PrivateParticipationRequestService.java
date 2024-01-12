package mainservice.participationrequest.service;

import lombok.RequiredArgsConstructor;
import mainservice.event.entity.Event;
import mainservice.event.entity.EventStateEnum;
import mainservice.event.repository.EventRepository;
import mainservice.exception.*;
import mainservice.participationrequest.dto.ParticipationRequestDto;
import mainservice.participationrequest.dto.ParticipationRequestEnum;
import mainservice.participationrequest.entity.ParticipationRequest;
import mainservice.participationrequest.mapper.ParticipationRequestToDtoMapper;
import mainservice.participationrequest.repository.ParticipationRequestRepository;
import mainservice.user.entity.User;
import mainservice.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.time.LocalDateTime.now;

@Service
@RequiredArgsConstructor
public class PrivateParticipationRequestService {

    private final ParticipationRequestRepository participationRequestRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final ParticipationRequestToDtoMapper participationRequestToDtoMapper;


    public ParticipationRequestDto postParticipationRequest(Long userId, Long eventId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EventNotFoundException("Event not found"));

        checkEventInitiatorNotRequester(userId, event);
        checkEventIsPublished(event);
        checkEventParticipantLimit(event);

        ParticipationRequestEnum status = null;
        if (event.getRequestModeration()) {
            status = ParticipationRequestEnum.PENDING;
        }
        if (!event.getRequestModeration()) {
            status = ParticipationRequestEnum.CONFIRMED;
            event.setConfirmedRequests(event.getConfirmedRequests() + 1);
        }
        ParticipationRequest participationRequest = ParticipationRequest
                .builder()
                .status(status)
                .event(event)
                .requester(user)
                .created(now())
                .build();
        participationRequestRepository.save(participationRequest);

        return participationRequestToDtoMapper.toDto(participationRequest);

    }


    private void checkEventInitiatorNotRequester(Long userId, Event event) {
        if (Objects.equals(event.getInitiator().getId(), userId)) {
            throw new RequesterIsInitiatorException("You are initiator of this event");
        }
    }

    private void checkEventIsPublished(Event event) {
        if (!event.getState().equals(EventStateEnum.PUBLISHED)) {
            throw new EventPublishedExceptions("Event is not Published");
        }
    }

    private void checkEventParticipantLimit(Event event) {
        if (Objects.equals(event.getParticipantLimit(), 0)
            && Objects.equals(event.getConfirmedRequests(), event.getParticipantLimit())) {
            throw new ParticipantLimitReachedException("Participant limit reached");
        }
    }


    public List<ParticipationRequestDto> getParticipationRequestList(Long userId) {

        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException("User not found");
        }

        return participationRequestRepository.findAllByUserId(userId)
                .stream()
                .map(participationRequestToDtoMapper::toDto)
                .collect(Collectors.toList());

    }


    public ParticipationRequestDto participationRequestCancel(Long userId, Long requestId) {

        ParticipationRequest participationRequest = participationRequestRepository
                .findById(requestId).orElseThrow(() -> new RequestNotFoundException("Request not found"));

        if (!Objects.equals(participationRequest.getRequester().getId(), userId)) {
            throw new ParticipationRequestRequesterException("You are not request owner");
        }
        participationRequest.setStatus(ParticipationRequestEnum.CANCELED);

        return participationRequestToDtoMapper.toDto(participationRequest);

    }

}

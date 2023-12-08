package mainservice.event.service;

import lombok.RequiredArgsConstructor;
import mainservice.category.entity.Category;
import mainservice.category.repository.CategoryRepository;
import mainservice.event.dto.*;
import mainservice.event.entity.Event;
import mainservice.event.entity.EventStateEnum;
import mainservice.event.mapper.EventPatchByUpdateEventUserRequestMapper;
import mainservice.event.mapper.EventToEventFullDtoMapper;
import mainservice.event.mapper.EventToEventShortDtoMapper;
import mainservice.event.mapper.NewEventDtoToEventMapper;
import mainservice.event.repository.EventRepository;
import mainservice.exception.*;
import mainservice.location.entity.Location;
import mainservice.location.repository.LocationRepository;
import mainservice.participationrequest.dto.EventRequestStatusUpdateRequest;
import mainservice.participationrequest.dto.EventRequestStatusUpdateResult;
import mainservice.participationrequest.dto.ParticipationRequestDto;
import mainservice.participationrequest.dto.ParticipationRequestEnum;
import mainservice.participationrequest.entity.ParticipationRequest;
import mainservice.participationrequest.mapper.ParticipationRequestToDtoMapper;
import mainservice.participationrequest.repository.ParticipationRequestRepository;
import mainservice.user.entity.User;
import mainservice.user.repository.UserRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.time.LocalTime.now;

@Service
@RequiredArgsConstructor
public class PrivateEventService {

    private final EventRepository eventRepository;
    private final CategoryRepository categoryRepository;
    private final LocationRepository locationRepository;
    private final UserRepository userRepository;
    private final NewEventDtoToEventMapper newEventDtoToEventMapper;
    private final EventToEventFullDtoMapper eventToEventFullDtoMapper;
    private final EventToEventShortDtoMapper eventToEventShortDtoMapper;
    private final EventPatchByUpdateEventUserRequestMapper eventPatchByUpdateEventUserRequestMapper;
    private final ParticipationRequestRepository participationRequestRepository;

    private final ParticipationRequestToDtoMapper participationRequestToDtoMapper;

    public EventFullDto addEvent(Long userId, NewEventDto newEventDto) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Category category = categoryRepository.findById(newEventDto.getCategory())
                .orElseThrow(() -> new CategoryNotFoundException("Category not found"));

        Location location = createIfNotExist(newEventDto.getLocation());

        Event event = newEventDtoToEventMapper.toEvent(newEventDto);
        event.setInitiator(user);
        event.setCategory(category);
        event.setLocation(location);
        event.setState(EventStateEnum.PENDING);
        event.setCreatedOn(LocalDateTime.from(now()));

        Event savedEvent = eventRepository.save(event);

        return eventToEventFullDtoMapper.toEventFullDto(savedEvent);

    }

    private Location createIfNotExist(Location location) {
        return locationRepository.findByLatAndLon(location.getLat(), location.getLon())
                .orElse(locationRepository.saveAndFlush(location));
    }


    public List<EventShortDto> getEventsByUserId(Long userId, Integer from, Integer size) {

        Pageable pageRequest = PageRequest.of(from, size);

        return eventRepository.getEventsByUserId(userId, pageRequest)
                .map(eventToEventShortDtoMapper::toEventShortDto)
                .getContent();

    }

    public EventFullDto getEventByUserIdAndEventId(Long userId, Long eventId) {
        return eventRepository.findById(eventId)
                .map(event -> {
                    checkUserIdAndInitiatorId(userId, event.getInitiator().getId());
                    return eventToEventFullDtoMapper.toEventFullDto(event);
                })
                .orElseThrow(() -> new EventNotFoundException("Event not found"));

    }

    private static void checkUserIdAndInitiatorId(Long userId, Long initiatorId) {
        if (!Objects.equals(userId, initiatorId)) {
            throw new EventInitiatorIdException("Initiator id check fail");
        }
    }

    public EventFullDto patchEventByUserInitiatorIdAndEventId(Long userId,
                                                              Long eventId,
                                                              UpdateEventUserRequest updateEventUserRequest) {
        Event event = eventRepository.findById(eventId)
                .map(e -> {
                    checkUserIdAndInitiatorId(userId, e.getInitiator().getId());
                    return e;
                })
                .orElseThrow(() -> new EventNotFoundException("Event not found"));

        if (EventStateEnum.PUBLISHED.equals(event.getState())) {
            throw new EventPublishedExceptions("It is not allow update published event");
        }

        eventPatchByUpdateEventUserRequestMapper.patchEvent(event, updateEventUserRequest);

        applyStateAction(updateEventUserRequest.getStateAction(), event);

        Event savedEvent = eventRepository.save(event);

        return eventToEventFullDtoMapper.toEventFullDto(savedEvent);
    }

    private void applyStateAction(StateAction stateAction, Event event) {
        switch (stateAction) {
            case CANCEL_REVIEW:
                event.setState(EventStateEnum.CANCELED);
                break;

            case SEND_TO_REVIEW:
                event.setState(EventStateEnum.PENDING);
                break;
            default:
                throw new InvalidStateActionException("Invalid State action");
        }
    }

    public List<ParticipationRequestDto> getParticipationRequestListByUserIdByEventId
            (Long userId, Long eventId) {

        Long initiatorId = eventRepository.findInitiatorIdByEventId(eventId);

        checkUserIdAndInitiatorId(userId, initiatorId);

        return participationRequestRepository.findAllByEventId(eventId)
                .stream()
                .map(participationRequestToDtoMapper::toDto)
                .collect(Collectors.toList());
    }

    public EventRequestStatusUpdateResult patchParticipationRequestStatus(
            Long userId,
            Long eventId,
            EventRequestStatusUpdateRequest eventRequestStatusUpdateRequest) {

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EventNotFoundException("Event not found"));

        checkUserIdAndInitiatorId(userId, event.getInitiator().getId());

        List<ParticipationRequest> participationRequestList = participationRequestRepository
                .findAllById(eventRequestStatusUpdateRequest.getRequestIds());

        ParticipationRequestEnum newStatus = eventRequestStatusUpdateRequest.getStatus();

        List<ParticipationRequestDto> confirmedRequests = new ArrayList<>();
        List<ParticipationRequestDto> rejectedRequests = new ArrayList<>();

        participationRequestList.forEach(
                request -> {
                    if (!ParticipationRequestEnum.PENDING.equals(request.getStatus())) {
                        throw new ParticipationRequestEnumException("Request status must be equal PENDING");
                    }
                    if (Objects.equals(event.getParticipantLimit(), event.getConfirmedRequests())) {
                        rejectAllPendingRequestByEventId(eventId);
                        throw new ParticipantLimitReachedException("Participant limit was reached.");
                    }
                    switch (newStatus) {

                        case CONFIRMED: {
                            confirmRequest(event, newStatus, confirmedRequests, request);
                            return;
                        }
                        case REJECTED: {
                            rejectRequest(newStatus, rejectedRequests, request);
                            return;
                        }
                        default: {
                            throw new InvalidStatusException("Unknown event status!");
                        }
                    }
                }
        );
        participationRequestRepository.saveAll(participationRequestList);

        eventRepository.save(event);

        return EventRequestStatusUpdateResult.builder()
                .confirmedRequests(confirmedRequests)
                .rejectedRequests(rejectedRequests)
                .build();
    }

    private void rejectAllPendingRequestByEventId(Long eventId) {
        participationRequestRepository
                .updateStatusByEventIdAndPending(eventId, ParticipationRequestEnum.REJECTED);
    }


    private void confirmRequest(Event event,
                                ParticipationRequestEnum newStatus,
                                List<ParticipationRequestDto> confirmedRequests,
                                ParticipationRequest request) {
        request.setStatus(newStatus);
        event.setConfirmedRequests(event.getConfirmedRequests() + 1);
        confirmedRequests.add(participationRequestToDtoMapper.toDto(request));
    }

    private void rejectRequest(ParticipationRequestEnum newStatus,
                               List<ParticipationRequestDto> rejectedRequests,
                               ParticipationRequest request) {
        request.setStatus(newStatus);
        rejectedRequests.add(participationRequestToDtoMapper.toDto(request));
    }

}

package mainservice.event.service;

import lombok.RequiredArgsConstructor;
import mainservice.event.dto.AdminEventStateAction;
import mainservice.event.dto.EventFullDto;
import mainservice.event.dto.UpdateEventAdminRequest;
import mainservice.event.dto.filter.AdminEventFilterQuery;
import mainservice.event.entity.Event;
import mainservice.event.entity.EventStateEnum;
import mainservice.event.mapper.EventToEventFullDtoMapper;
import mainservice.event.mapper.UpdateEventAdminRequestToEventMapper;
import mainservice.event.repository.EventRepository;
import mainservice.exception.EventNotFoundException;
import mainservice.exception.InvalidStateActionException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static java.time.LocalDateTime.now;

@Service
@RequiredArgsConstructor
public class AdminEventService {

    private final EventRepository eventRepository;
    private final EventToEventFullDtoMapper eventToEventFullDtoMapper;
    private final UpdateEventAdminRequestToEventMapper updateEventAdminRequestToEventMapper;

    public List<EventFullDto> getEventsList(AdminEventFilterQuery filterQuery) {
        return eventRepository.getEventsListForAdmin(filterQuery).stream()
                .map(eventToEventFullDtoMapper::toEventFullDto)
                .collect(Collectors.toList());

    }

    public EventFullDto patchEvent(Long eventId,
                                   UpdateEventAdminRequest updateEventAdminRequest) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EventNotFoundException("Requested Event not found"));

        Event updated = updateEventAdminRequestToEventMapper.toEvent(updateEventAdminRequest,
                event);

        if (updateEventAdminRequest.getStateAction() != null) {
            applyStateAction(updateEventAdminRequest.getStateAction(), updated);
        }

        Event saved = eventRepository.save(updated);

        return eventToEventFullDtoMapper.toEventFullDto(saved);

    }

    private void applyStateAction(AdminEventStateAction adminEventStateAction,
                                  Event event) {
        switch (adminEventStateAction) {
            case PUBLISH_EVENT: {
                if (!EventStateEnum.PENDING.equals(event.getState())) {
                    throw new InvalidStateActionException("You cannot publish event if its state is not PENDING.");
                }
                event.setState(EventStateEnum.PUBLISHED);
                event.setPublishedOn(now());
                return;
            }
            case REJECT_EVENT: {
                if (EventStateEnum.PUBLISHED.equals(event.getState())) {
                    throw new InvalidStateActionException("You cannot reject event if it is already published.");
                }
                event.setState(EventStateEnum.CANCELED);
                return;
            }
            default: {
                throw new InvalidStateActionException("Unknown state action");
            }
        }
    }

}

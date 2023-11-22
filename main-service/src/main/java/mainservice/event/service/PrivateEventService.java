package mainservice.event.service;

import lombok.RequiredArgsConstructor;
import mainservice.category.entity.Category;
import mainservice.category.repository.CategoryRepository;
import mainservice.event.dto.*;
import mainservice.event.entity.Event;
import mainservice.event.entity.EventStateEnum;
import mainservice.event.mapper.EventPatchByUpdateEventUserRequestMapper;
import mainservice.event.mapper.EventToEventFullDtoMapper;
import mainservice.event.mapper.EventToEventShortDto;
import mainservice.event.mapper.NewEventDtoToEventMapper;
import mainservice.event.repository.EventRepository;
import mainservice.exception.*;
import mainservice.location.entity.Location;
import mainservice.location.repository.LocationRepository;
import mainservice.user.entity.User;
import mainservice.user.repository.UserRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

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
    private final EventToEventShortDto eventToEventShortDto;
    private final EventPatchByUpdateEventUserRequestMapper eventPatchByUpdateEventUserRequestMapper;

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
        event.setCreatedOn(String.valueOf(now()));

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
                .map(eventToEventShortDto::toEventShortDto)
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


}

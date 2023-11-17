package mainservice.event.service;

import lombok.RequiredArgsConstructor;
import mainservice.category.entity.Category;
import mainservice.category.repository.CategoryRepository;
import mainservice.event.dto.EventFullDto;
import mainservice.event.dto.NewEventDto;
import mainservice.event.entity.Event;
import mainservice.event.entity.EventStateEnum;
import mainservice.event.mapper.EventToEventFullDtoMapper;
import mainservice.event.mapper.NewEventDtoToEventMapper;
import mainservice.event.repository.EventRepository;
import mainservice.exception.CategoryNotFoundException;
import mainservice.exception.UserNotFoundException;
import mainservice.location.entity.Location;
import mainservice.location.repository.LocationRepository;
import mainservice.user.entity.User;
import mainservice.user.repository.UserRepository;
import org.springframework.stereotype.Service;

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
        return locationRepository.findByLatAndLog(location.getLat(), location.getLon())
                .orElse(locationRepository.saveAndFlush(location));
    }


}

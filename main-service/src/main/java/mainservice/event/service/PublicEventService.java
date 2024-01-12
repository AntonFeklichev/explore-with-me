package mainservice.event.service;

import lombok.RequiredArgsConstructor;
import mainservice.event.dto.EventShortDto;
import mainservice.event.dto.filter.PublicEventFilterQuery;
import mainservice.event.mapper.EventToEventShortDtoMapper;
import mainservice.event.repository.EventRepository;
import mainservice.exception.ValidationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PublicEventService {

    private final EventRepository eventRepository;
    private final EventToEventShortDtoMapper eventToEventShortDtoMapper;


    public List<EventShortDto> getFilteredEventForPublic(PublicEventFilterQuery publicEventFilterQuery) {

        if (publicEventFilterQuery.getRangeEnd() != null && publicEventFilterQuery.getRangeEnd().isBefore(publicEventFilterQuery.getRangeStart())) {
            throw new ValidationException("Invalid end event date");
        }
        return eventRepository.getFilteredEventForPublic(publicEventFilterQuery).stream()
                .map(eventToEventShortDtoMapper::toEventShortDto)
                .collect(Collectors.toList());

    }

}

package mainservice.event.service;

import lombok.RequiredArgsConstructor;
import mainservice.event.dto.EventFullDto;
import mainservice.event.dto.filter.AdminEventFilterQuery;
import mainservice.event.mapper.EventToEventFullDtoMapper;
import mainservice.event.repository.EventRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminEventService {

    private final EventRepository eventRepository;
    private final EventToEventFullDtoMapper eventToEventFullDtoMapper;

    public List<EventFullDto> getEventsList(AdminEventFilterQuery filterQuery) {
        return eventRepository.getEventsListForAdmin(filterQuery).stream()
                .map(eventToEventFullDtoMapper::toEventFullDto)
                .collect(Collectors.toList());

    }


}

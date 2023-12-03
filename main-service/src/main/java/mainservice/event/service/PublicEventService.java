package mainservice.event.service;

import lombok.RequiredArgsConstructor;
import mainservice.event.dto.EventShortDto;
import mainservice.event.dto.filter.PublicEventFilterQuery;
import mainservice.event.mapper.EventToEventShortDto;
import mainservice.event.repository.EventRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PublicEventService {

    private final EventRepository eventRepository;
    private final EventToEventShortDto eventToEventShortDto;


    public List<EventShortDto> getFilteredEventForPublic(PublicEventFilterQuery publicEventFilterQuery) {

        return eventRepository.getFilteredEventForPublic(publicEventFilterQuery).stream()
                .map(eventToEventShortDto::toEventShortDto)
                .collect(Collectors.toList());

    }

}

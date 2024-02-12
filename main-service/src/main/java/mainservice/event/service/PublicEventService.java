package mainservice.event.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import endpointhit.EndPointHitDto;
import endpointhitclient.EndPointHitClient;
import lombok.RequiredArgsConstructor;
import mainservice.event.dto.EventFullDto;
import mainservice.event.dto.EventShortDto;
import mainservice.event.dto.filter.PublicEventFilterQuery;
import mainservice.event.entity.Event;
import mainservice.event.entity.EventStateEnum;
import mainservice.event.mapper.EventToEventFullDtoMapper;
import mainservice.event.mapper.EventToEventShortDtoMapper;
import mainservice.event.repository.EventRepository;
import mainservice.exception.EventNotFoundException;
import mainservice.exception.ValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import stats.dto.StatsIpDto;
import statsclient.StatsClient;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PublicEventService {

    private final EventRepository eventRepository;
    private final StatsClient statsClient;
    private final EndPointHitClient endPointHitClient;
    private final EventToEventShortDtoMapper eventToEventShortDtoMapper;
    private final EventToEventFullDtoMapper eventToEventFullDtoMapper;


    public List<EventShortDto> getFilteredEventForPublic(PublicEventFilterQuery publicEventFilterQuery) {

        if (publicEventFilterQuery.getRangeEnd() != null && publicEventFilterQuery.getRangeEnd().isBefore(publicEventFilterQuery.getRangeStart())) {
            throw new ValidationException("Invalid end event date");
        }
        return eventRepository.getFilteredEventForPublic(publicEventFilterQuery).stream()
                .map(eventToEventShortDtoMapper::toEventShortDto)
                .collect(Collectors.toList());
    }

    public EventFullDto getEventById(Long eventId, HttpServletRequest request) {


        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EventNotFoundException("Event not found"));
        if (!event.getState().equals(EventStateEnum.PUBLISHED)) {
            throw new EventNotFoundException("Event is not Published");
        }
        ResponseEntity<Object> statServerResponse = statsClient
                .getStatsIp(request.getRemoteAddr(), request.getRequestURI());


        ObjectMapper mapper = new ObjectMapper();

        StatsIpDto statsIpDto = mapper.convertValue(statServerResponse.getBody(), StatsIpDto.class);

        if(!statsIpDto.getIsIpHit()) {
        event.setViews(event.getViews() + 1);}

        EndPointHitDto endPointHitDto = EndPointHitDto.builder()
                .app("explore-with-me-main-service")
                .ip(request.getRemoteAddr())
                .uri(request.getRequestURI())
                .timestamp(LocalDateTime.now())
                .build();

        endPointHitClient.saveEndPointHit(endPointHitDto);


        eventRepository.save(event);

        return eventToEventFullDtoMapper.toEventFullDto(event);

    }


}

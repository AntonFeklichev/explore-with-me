package mainservice.event.controller;

import endpointhit.EndPointHitDto;
import endpointhitclient.EndPointHitClient;
import lombok.RequiredArgsConstructor;
import mainservice.event.dto.EventShortDto;
import mainservice.event.dto.EventSort;
import mainservice.event.dto.filter.PublicEventFilterQuery;
import mainservice.event.service.PublicEventService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(path = "/events")
@Validated
@RequiredArgsConstructor
public class PublicEventController {

    private final EndPointHitClient endPointHitClient;
    private final PublicEventService publicEventService;

    @GetMapping
    List<EventShortDto> getFilteredEventForPublic(@RequestParam(name = "text", required = false)
                                                  String text,
                                                  @RequestParam(name = "categories", required = false)
                                                  List<Long> categories,
                                                  @RequestParam(name = "paid", required = false)
                                                  Boolean paid,
                                                  @RequestParam(name = "rangeStart", required = false)
                                                  LocalDateTime rangeStart,
                                                  @RequestParam(name = "rangeEnd", required = false)
                                                  LocalDateTime rangeEnd,
                                                  @RequestParam(name = "onlyAvailable", defaultValue = "false")
                                                  Boolean onlyAvailable,
                                                  @RequestParam(name = "sort", defaultValue = "EVENT_DATE")
                                                  EventSort sort,
                                                  @RequestParam(name = "from", defaultValue = "0")
                                                  Integer from,
                                                  @RequestParam(name = "size", defaultValue = "10")
                                                  Integer size,
                                                  HttpServletRequest request) {

        EndPointHitDto endPointHitDto = EndPointHitDto.builder()
                .app("explore-with-me-main-service")
                .ip(request.getRemoteAddr())
                .uri(request.getRequestURI())
                .timestamp(LocalDateTime.now())
                .build();

        endPointHitClient.saveEndPointHit(endPointHitDto);

        PublicEventFilterQuery query = PublicEventFilterQuery.builder()
                .sort(sort)
                .text(text)
                .paid(paid)
                .onlyAvailable(onlyAvailable)
                .categories(categories)
                .rangeStart(rangeStart)
                .rangeEnd(rangeEnd)
                .from(from)
                .size(size)
                .build();

        return publicEventService.getFilteredEventForPublic(query);
    }
}

package mainservice.event.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.RequiredArgsConstructor;
import mainservice.event.dto.EventFullDto;
import mainservice.event.dto.UpdateEventAdminRequest;
import mainservice.event.dto.filter.AdminEventFilterQuery;
import mainservice.event.entity.EventStateEnum;
import mainservice.event.service.AdminEventService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RequestMapping(path = "/admin")
@RestController
@RequiredArgsConstructor
@Validated
public class AdminEventController {

    private static final String DATETIME_FORMAT_PATTERN = "yyyy-MM-dd HH:mm:ss";
    private final AdminEventService adminEventService;


    @GetMapping(path = "/events")
    public List<EventFullDto> getEventsList(@RequestParam(name = "usersIds", defaultValue = "")
                                            List<Long> usersIds,
                                            @RequestParam(name = "states", defaultValue = "")
                                            List<EventStateEnum> states,
                                            @RequestParam(name = "categories", defaultValue = "")
                                            List<Long> categories,
                                            @RequestParam(name = "rangeStart", required = false)
                                            @JsonFormat(shape = JsonFormat.Shape.STRING,
                                                    pattern = DATETIME_FORMAT_PATTERN)
                                            LocalDateTime rangeStart,
                                            @RequestParam(name = "rangeEnd", required = false)
                                            @JsonFormat(shape = JsonFormat.Shape.STRING,
                                                    pattern = DATETIME_FORMAT_PATTERN)
                                            LocalDateTime rangeEnd,
                                            @RequestParam(name = "from", defaultValue = "0")
                                            Integer from,
                                            @RequestParam(name = "size", defaultValue = "10")
                                            Integer size
    ) {
        AdminEventFilterQuery filterQuery = AdminEventFilterQuery.builder()
                .usersIds(usersIds)
                .states(states)
                .categories(categories)
                .rangeStart(rangeStart)
                .rangeEnd(rangeEnd)
                .from(from)
                .size(size)
                .build();

        return adminEventService.getEventsList(filterQuery);
    }

    @PatchMapping(path = "/events/{eventId}")
    public EventFullDto patchEvent(@PathVariable(name = "eventId")
                                   Long eventId,
                                   @RequestBody
                                   UpdateEventAdminRequest updateEventAdminRequest) {

        return adminEventService.patchEvent(eventId, updateEventAdminRequest);
    }

}

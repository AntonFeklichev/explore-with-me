package mainservice.event.controller;

import lombok.RequiredArgsConstructor;
import mainservice.event.dto.EventFullDto;
import mainservice.event.dto.EventShortDto;
import mainservice.event.dto.NewEventDto;
import mainservice.event.dto.UpdateEventUserRequest;
import mainservice.event.service.PrivateEventService;
import mainservice.participationrequest.dto.EventRequestStatusUpdateRequest;
import mainservice.participationrequest.dto.EventRequestStatusUpdateResult;
import mainservice.participationrequest.dto.ParticipationRequestDto;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Validated
@RestController
@RequestMapping(path = "/users")
@RequiredArgsConstructor
public class PrivateEventController {

    private final PrivateEventService privateEventService;

    @PostMapping(path = "/{userId}/events")
    @ResponseStatus(value = HttpStatus.CREATED)
    public EventFullDto addEvent(@PathVariable(name = "userId")
                                 Long userId,
                                 @RequestBody
                                 @Valid
                                 NewEventDto newEventDto) {

        return privateEventService.addEvent(userId, newEventDto);

    }

    @GetMapping(path = "/{userId}/events")
    @ResponseStatus(HttpStatus.OK)

    public List<EventShortDto> getEventsByUserId(@PathVariable(name = "userId", required = true)
                                                 Long userId,
                                                 @RequestParam(name = "from", defaultValue = "0")
                                                 Integer from,
                                                 @RequestParam(name = "size", defaultValue = "10")
                                                 Integer size) {

        return privateEventService.getEventsByUserId(userId, from, size);

    }

    @GetMapping(path = "/{userId}/events/{eventId}")
    @ResponseStatus(HttpStatus.OK)

    public EventFullDto getEventByUserIdAndEventId(@PathVariable(name = "userId")
                                                   Long userId,
                                                   @PathVariable(name = "eventId")
                                                   Long eventId) {

        return privateEventService.getEventByUserIdAndEventId(userId, eventId);
    }

    @PatchMapping(path = "/{userId}/events/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    public EventFullDto patchEventByUserInitiatorIdAndEventId(@PathVariable(name = "userId")
                                                              Long userId,
                                                              @PathVariable(name = "eventId")
                                                              Long eventId,
                                                              @Valid
                                                              @RequestBody
                                                              UpdateEventUserRequest updateEventUserRequest) {

        return privateEventService.patchEventByUserInitiatorIdAndEventId(userId,
                eventId,
                updateEventUserRequest);
    }

    @GetMapping(path = "/{userId}/events/{eventId}/requests")
    @ResponseStatus(HttpStatus.OK)
    public List<ParticipationRequestDto> getParticipationRequestListByUserIdByEventId(
            @PathVariable(name = "userId")
            Long userId,
            @PathVariable(name = "eventId")
            Long eventId) {

        return privateEventService.getParticipationRequestListByUserIdByEventId(userId, eventId);
    }

    @PatchMapping(path = "/{userId}/events/{eventId}/requests")
    @ResponseStatus(HttpStatus.OK)
    public EventRequestStatusUpdateResult patchParticipationRequestStatus(@PathVariable(name = "userId")
                                                                          Long userId,
                                                                          @PathVariable(name = "eventId")
                                                                          Long eventId,
                                                                          @RequestBody
                                                                          EventRequestStatusUpdateRequest eventRequestStatusUpdateRequest) {

        return privateEventService.patchParticipationRequestStatus(
                userId,
                eventId,
                eventRequestStatusUpdateRequest);
    }


}

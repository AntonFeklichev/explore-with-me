package mainservice.event.controller;

import lombok.RequiredArgsConstructor;
import mainservice.event.dto.EventFullDto;
import mainservice.event.dto.NewEventDto;
import mainservice.event.service.PrivateEventService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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

}

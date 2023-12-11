package mainservice.participationrequest.controller;

import lombok.RequiredArgsConstructor;
import mainservice.participationrequest.dto.ParticipationRequestDto;
import mainservice.participationrequest.service.PrivateParticipationRequestService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/users")
@RequiredArgsConstructor
@Validated
public class PrivateParticipationRequestController {

    private final PrivateParticipationRequestService privateParticipationRequestService;

    @PatchMapping(path = "/{userId}/requests")
    @ResponseStatus(HttpStatus.CREATED)
    public ParticipationRequestDto patchParticipationRequest(@PathVariable(name = "userId")
                                                             Long userId,
                                                             @RequestParam(name = "eventId")
                                                             Long eventId) {

        return privateParticipationRequestService.patchParticipationRequest(userId, eventId);

    }

    @GetMapping(path = "/{userId}/requests")
    @ResponseStatus(HttpStatus.OK)
    public List<ParticipationRequestDto> getParticipationRequestList(@PathVariable(name = "userId")
                                                                     Long userId) {

        return privateParticipationRequestService.getParticipationRequestList(userId);
    }

    @PatchMapping(path = "/{userId}/requests/{requestId}/cancel")
    public ParticipationRequestDto participationRequestCancel(@PathVariable(name = "userId")
                                                              Long userId,
                                                              @PathVariable(name = "requestId")
                                                              Long requestId) {

        return privateParticipationRequestService.participationRequestCancel(userId, requestId);

    }

}

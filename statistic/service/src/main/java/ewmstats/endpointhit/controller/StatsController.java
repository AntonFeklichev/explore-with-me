package ewmstats.endpointhit.controller;

import endpointhit.EndPointHitDto;
import ewmstats.endpointhit.service.StatsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import stats.dto.StatsDto;
import stats.dto.StatsRequestDto;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@RestController
@Slf4j
@Validated

public class StatsController {

    private final StatsService statsService;

    @PostMapping("/hit")
    public void saveEndPointHit(@RequestBody
                                EndPointHitDto endPointHitDto) {
        statsService.saveEndPointHit(endPointHitDto);

    }

    @GetMapping("/stats")
    public List<StatsDto> getStats(@RequestParam(name = "start")
                                   LocalDateTime start,
                                   @RequestParam(name = "end")
                                   LocalDateTime end,
                                   @RequestParam(name = "uris",
                                           defaultValue = "")
                                   List<String> uris,
                                   @RequestParam(name = "unique",
                                           defaultValue = "false")
                                   Boolean unique) {


        StatsRequestDto statsRequestDto = StatsRequestDto.builder()
                .start(start)
                .end(end)
                .uris(uris)
                .unique(unique)
                .build();
        return statsService.getStats(statsRequestDto);
    }

}

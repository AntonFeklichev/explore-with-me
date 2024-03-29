package ewmstats.endpointhit.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import endpointhit.EndPointHitDto;
import ewmstats.endpointhit.service.StatsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import stats.dto.StatsDto;
import stats.dto.StatsIpDto;
import stats.dto.StatsRequestDto;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@RestController
@Slf4j
@Validated

public class StatsController {

    private final StatsService statsService;


    private static final String DATETIME_FORMAT_PATTERN = "yyyy-MM-dd HH:mm:ss";


    @PostMapping("/hit")
    public void saveEndPointHit(@RequestBody
                                EndPointHitDto endPointHitDto) {
        statsService.saveEndPointHit(endPointHitDto);

    }

    @GetMapping("/stats")
    public List<StatsDto> getStats(@RequestParam(name = "start")
                                   @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATETIME_FORMAT_PATTERN)
                                   LocalDateTime start,
                                   @RequestParam(name = "end")
                                   @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATETIME_FORMAT_PATTERN)
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

    @GetMapping("/stats/ip")
    public StatsIpDto getStatsIpDto(@RequestParam(name = "ip")
                                    String ip,
                                    @RequestParam(name = "uri")
                                    String uri) {

        return statsService.getStatsIpDto(ip, uri);

    }


}

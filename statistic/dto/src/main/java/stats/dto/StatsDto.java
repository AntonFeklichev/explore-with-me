package stats.dto;

import lombok.Builder;

@Builder
public class StatsDto {

    private String app;

    private String uri;
    private Long hits;

}

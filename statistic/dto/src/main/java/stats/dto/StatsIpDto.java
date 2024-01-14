package stats.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class StatsIpDto {
    Boolean isIpHit;
}

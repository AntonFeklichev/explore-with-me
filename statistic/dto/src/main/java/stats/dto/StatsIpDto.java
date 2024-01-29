package stats.dto;

import lombok.*;
import lombok.extern.jackson.Jacksonized;
;


@Value
@Builder
@Jacksonized
public class StatsIpDto {

    Boolean isIpHit;

}

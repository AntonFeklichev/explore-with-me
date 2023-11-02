package endPointHit.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public class EndPointHitDto {

    private String app;
    private String uri;
    private String ip;
    private LocalDateTime timestamp;


}

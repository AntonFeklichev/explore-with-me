package endpointhit;

import lombok.Builder;
import lombok.Data;


import java.time.LocalDateTime;

@Builder
@Data
public class EndPointHitDto {

    private String app;
    private String uri;
    private String ip;
    private LocalDateTime timestamp;


}

package mainservice.participationrequest.dto;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder
public class ParticipationRequestDto {

    Long id;
    LocalDateTime created;
    Long event;
    Long requester;
    ParticipationRequestEnum status;


}

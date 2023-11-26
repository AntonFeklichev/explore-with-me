package mainservice.participationrequest.dto;

import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Value
@Builder
public class EventRequestStatusUpdateRequest {

    @NotEmpty
    List<Long> requestIds;
    @NotNull
    ParticipationRequestEnum status;

}

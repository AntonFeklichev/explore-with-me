package mainservice.event.dto;

import lombok.Builder;
import lombok.Value;

import mainservice.location.entity.Location;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Value
@Builder
public class UpdateEventUserRequest {
    @Size(min = 20, max = 2000)
    String annotation;
    Long category;
    @Size(min = 20, max = 7000)
    String description;
    @FutureOrPresent
    LocalDateTime eventDate;
    Location location;
    Boolean paid;
    Integer participantLimit;
    Boolean requestModeration;
    StateAction stateAction;
    @Size(min = 3, max = 120)
    String title;


}

package mainservice.event.dto;

import lombok.Builder;
import lombok.Value;
import mainservice.location.entity.Location;

import javax.validation.constraints.*;
import java.time.LocalDateTime;

@Value
@Builder
public class NewEventDto {

    @NotBlank
    @Size(min = 20, max = 2000)
    String annotation;
    Long category;
    @NotBlank
    @Size(min = 20, max = 7000)
    String description;
    @FutureOrPresent
    LocalDateTime eventDate;
    Location location;
    Boolean paid = false;
    Integer participantLimit = 0;
    Boolean requestModeration = true;
    @Size(min = 3, max = 120)
    String title;

}

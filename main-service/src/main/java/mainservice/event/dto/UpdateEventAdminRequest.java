package mainservice.event.dto;

import lombok.Builder;
import lombok.Value;
import mainservice.location.entity.Location;

import java.time.LocalDateTime;

@Value
@Builder
public class UpdateEventAdminRequest {
    String annotation;
    Long categoryId;
    String description;
    LocalDateTime eventDate;
    Location location;
    Boolean paid;
    Integer participantLimit;
    Boolean requestModeration;
    AdminEventStateAction stateAction;
    String title;

}

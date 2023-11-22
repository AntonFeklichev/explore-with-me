package mainservice.event.dto;

import lombok.Builder;
import lombok.Value;

import mainservice.location.entity.Location;

@Value
@Builder
public class UpdateEventUserRequest {

    String annotation;
    Long category;
    String description;
    String eventDate;
    Location location;
    Boolean paid;
    Integer participantLimit;
    Boolean requestModeration;
    StateAction stateAction;
    String title;


}

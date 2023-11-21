package mainservice.event.dto;

import lombok.Builder;
import lombok.Value;
import mainservice.category.entity.Category;
import mainservice.event.entity.EventStateEnum;
import mainservice.location.entity.Location;

@Value
@Builder
public class UpdateEventUserRequest {

    String annotation;
    Category category;
    String description;
    String eventDate;
    Location location;
    Boolean paid;
    Integer participantLimit;
    Boolean requestModeration;
    StateAction stateAction;
    String title;


}

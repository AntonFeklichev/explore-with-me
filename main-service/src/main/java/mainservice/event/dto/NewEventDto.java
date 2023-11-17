package mainservice.event.dto;

import lombok.Builder;
import lombok.Value;
import mainservice.category.entity.Category;
import mainservice.location.entity.Location;

@Value
@Builder
public class NewEventDto {

    String annotation;
    Long category;
    String description;
    String eventDate;
    Location location;
    Boolean paid;
    Integer participantLimit;
    Boolean requestModeration;
    String title;

}

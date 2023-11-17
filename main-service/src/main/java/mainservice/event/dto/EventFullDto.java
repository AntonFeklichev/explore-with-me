package mainservice.event.dto;

import lombok.Builder;
import lombok.Value;
import mainservice.category.entity.Category;
import mainservice.event.entity.EventStateEnum;
import mainservice.location.entity.Location;
import mainservice.user.dto.UserShortDto;


@Value
@Builder
public class EventFullDto {

    Long id;
    String annotation;
    Category category;
    Integer confirmedRequests;
    String createdOn;
    String description;
    String eventDate;
    UserShortDto initiator;
    Location location;
    Boolean paid;
    Integer participantLimit;
    String publishedOn;
    Boolean requestModeration;
    EventStateEnum state;
    String title;
    Long views;

}

package mainservice.event.dto;

import lombok.Builder;
import lombok.Value;
import mainservice.category.dto.CategoryDto;
import mainservice.category.entity.Category;
import mainservice.event.entity.EventStateEnum;
import mainservice.location.entity.Location;
import mainservice.user.dto.UserShortDto;

import javax.validation.constraints.Size;
import java.time.LocalDateTime;


@Value
@Builder
public class EventFullDto {

    Long id;
    String annotation;
    CategoryDto category;
    Integer confirmedRequests;
    String createdOn;
    String description;
    LocalDateTime eventDate;
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

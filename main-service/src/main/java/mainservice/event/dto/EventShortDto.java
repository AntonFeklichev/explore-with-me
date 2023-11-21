package mainservice.event.dto;

import lombok.Builder;
import lombok.Value;
import mainservice.category.dto.CategoryDto;
import mainservice.category.entity.Category;
import mainservice.user.dto.UserShortDto;

@Value
@Builder
public class EventShortDto {

    Long id;
    String annotation;
    CategoryDto category;
    Integer confirmedRequests;
    String eventDate;
    UserShortDto initiator;
    Boolean paid;
    String title;
    Long views;

}

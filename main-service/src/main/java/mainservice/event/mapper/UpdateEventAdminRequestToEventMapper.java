package mainservice.event.mapper;

import mainservice.event.dto.UpdateEventAdminRequest;
import mainservice.event.entity.Event;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface UpdateEventAdminRequestToEventMapper {

    @Mapping(source = "categoryId", target = "category.id") //TODO Уточнить у Матвея
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Event toEvent(UpdateEventAdminRequest updateEventAdminRequest,
                  @MappingTarget Event event);

}

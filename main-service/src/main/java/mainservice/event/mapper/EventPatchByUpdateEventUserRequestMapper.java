package mainservice.event.mapper;

import mainservice.event.dto.UpdateEventUserRequest;
import mainservice.event.entity.Event;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface EventPatchByUpdateEventUserRequestMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "category.id", source = "category")
    void patchEvent(@MappingTarget Event event,
                    UpdateEventUserRequest updateEventUserRequest);

}

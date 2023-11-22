package mainservice.event.mapper;

import mainservice.category.entity.Category;
import mainservice.event.dto.NewEventDto;
import mainservice.event.entity.Event;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface NewEventDtoToEventMapper {

    @Mapping(target = "category", source = "category", qualifiedByName = "longToCategory")
    Event toEvent(NewEventDto newEventDto);

    @Named(value = "longToCategory")
    default Category longToCategory(Long categoryId) {
        return Category.builder()
                .id(categoryId)
                .build();
    }


}

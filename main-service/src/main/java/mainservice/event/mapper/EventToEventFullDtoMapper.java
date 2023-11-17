package mainservice.event.mapper;

import mainservice.event.dto.EventFullDto;
import mainservice.event.entity.Event;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface EventToEventFullDtoMapper {
    EventFullDto toEventFullDto(Event event);
}

package mainservice.event.mapper;

import mainservice.event.dto.EventShortDto;
import mainservice.event.entity.Event;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface EventToEventShortDtoMapper {

    EventShortDto toEventShortDto(Event event);
}

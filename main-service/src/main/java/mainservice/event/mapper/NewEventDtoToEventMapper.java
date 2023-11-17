package mainservice.event.mapper;

import mainservice.event.dto.NewEventDto;
import mainservice.event.entity.Event;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface NewEventDtoToEventMapper {

    Event toEvent(NewEventDto newEventDto);

}

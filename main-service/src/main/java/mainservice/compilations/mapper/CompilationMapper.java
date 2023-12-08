package mainservice.compilations.mapper;

import mainservice.compilations.dto.CompilationDto;
import mainservice.compilations.dto.NewCompilationDto;
import mainservice.compilations.dto.UpdateCompilationRequest;
import mainservice.compilations.entity.Compilation;
import mainservice.event.entity.Event;
import mainservice.event.mapper.EventToEventShortDtoMapper;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = EventToEventShortDtoMapper.class)
public interface CompilationMapper {

    Compilation toCompilation(NewCompilationDto newCompilationDto);


    default Event mapEvent(Long eventId) {
        return Event.builder()
                .id(eventId)
                .build();
    }


    CompilationDto toCompilationDto(Compilation compilation);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "events", target = "events")
    Compilation toCompilationFromUpdateRequest(UpdateCompilationRequest updateCompilationRequest);

}

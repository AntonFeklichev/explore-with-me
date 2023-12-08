package mainservice.compilations.dto;

import lombok.Builder;
import lombok.Value;
import mainservice.event.dto.EventShortDto;

import javax.persistence.Column;
import java.util.List;

@Value
@Builder
public class CompilationDto {

    Long id;
    List<EventShortDto> events;

    Boolean pinned;

    String title;

}

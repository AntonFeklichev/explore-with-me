package mainservice.compilations.dto;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class NewCompilationDto {

    List<Long> events;

    Boolean pinned;

    String title;


}

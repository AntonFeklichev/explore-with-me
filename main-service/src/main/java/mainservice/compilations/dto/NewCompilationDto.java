package mainservice.compilations.dto;

import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Value
@Builder
public class NewCompilationDto {

    List<Long> events;

    Boolean pinned;
    @NotBlank
    @NotNull
    @Size(max = 50)
    String title;


}

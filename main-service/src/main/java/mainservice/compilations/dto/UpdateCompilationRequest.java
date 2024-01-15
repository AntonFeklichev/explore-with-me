package mainservice.compilations.dto;

import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.Size;
import java.util.List;

@Value
@Builder
public class UpdateCompilationRequest {

    List<Long> events;
    Boolean pinned;
    @Size(max = 50)
    String title;

}

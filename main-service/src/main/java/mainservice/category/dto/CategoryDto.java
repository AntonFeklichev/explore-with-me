package mainservice.category.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CategoryDto {
    Long id;

    String name;

    @JsonCreator
    public CategoryDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}

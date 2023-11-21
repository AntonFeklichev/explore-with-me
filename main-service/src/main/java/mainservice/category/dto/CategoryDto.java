package mainservice.category.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CategoryDto {

    Long id;
    String name;

}

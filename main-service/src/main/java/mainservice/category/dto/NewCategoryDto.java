package mainservice.category.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotBlank;

@Value
@Builder
public class NewCategoryDto {

    @NotBlank(message = "Category name must not blank")
    String name;

    @JsonCreator
    public NewCategoryDto(@JsonProperty("name")
                          String name) {
        this.name = name;
    }
}

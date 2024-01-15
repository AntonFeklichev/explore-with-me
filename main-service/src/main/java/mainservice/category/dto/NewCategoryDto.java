package mainservice.category.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Value
@Builder
public class NewCategoryDto {

    @NotBlank(message = "Category name must not blank")
    @Size(max = 50)
    String name;

    @JsonCreator
    public NewCategoryDto(@JsonProperty("name")
                          String name) {
        this.name = name;
    }
}

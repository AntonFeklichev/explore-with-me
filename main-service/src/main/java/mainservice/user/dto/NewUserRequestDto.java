package mainservice.user.dto;

import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Value
@Builder
public class NewUserRequestDto {

    @NotBlank
    String name;

    @NotBlank
    @Email
    String email;

}

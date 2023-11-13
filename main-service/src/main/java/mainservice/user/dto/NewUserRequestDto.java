package mainservice.user.dto;

import lombok.Value;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Value
public class NewUserRequestDto {

    @NotBlank
    String name;

    @NotBlank
    @Email
    String email;

}

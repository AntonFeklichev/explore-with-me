package mainservice.user.controller;

import lombok.RequiredArgsConstructor;
import mainservice.user.dto.NewUserRequestDto;
import mainservice.user.dto.UserDto;
import mainservice.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/admin/users")
@Validated
@RequiredArgsConstructor
public class AdminUserController {

    private final UserService userService;

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public UserDto addUser(@RequestBody
                           @Valid
                           NewUserRequestDto newUserRequestDto) {

        return userService.addUser(newUserRequestDto);
    }

    @GetMapping
    public List<UserDto> getUsers(@RequestParam(name = "ids", required = false)
                                  List<Long> ids,
                                  @RequestParam(name = "from", defaultValue = "0")
                                  Integer from,
                                  @RequestParam(name = "size", defaultValue = "10")
                                  Integer size) {

        return userService.getUsers(ids, from, size).getContent();

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable(name = "id")
                                           Long userId) {
        userService.deleteById(userId);
        return ResponseEntity.noContent().build();
    }


}

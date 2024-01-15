package mainservice.user.service;

import lombok.RequiredArgsConstructor;
import mainservice.exception.UserAlreadyExistsException;
import mainservice.user.dto.NewUserRequestDto;
import mainservice.user.dto.UserDto;
import mainservice.user.entity.User;
import mainservice.user.mapper.NewUserRequestDtoMapper;
import mainservice.user.mapper.UserDtoMapper;
import mainservice.user.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final NewUserRequestDtoMapper newUserRequestDtoMapper;
    private final UserDtoMapper userDtoMapper;

    public UserDto addUser(NewUserRequestDto newUserRequestDto) {

        User user = newUserRequestDtoMapper.toUser(newUserRequestDto);

        if (userRepository.existsByName(user.getName())) {
            throw new UserAlreadyExistsException("User already exists");
        }
        userRepository.save(user);

        return userDtoMapper.toUserDto(user);
    }

    public Page<UserDto> getUsers(List<Long> ids, Integer from, Integer size) {

        Pageable pageRequest = PageRequest.of(from, size);

        if (ids == null) {
            return userRepository.findAll(pageRequest).map(userDtoMapper::toUserDto);
        }
        return userRepository.findAllByIdIn(ids, pageRequest).map(userDtoMapper::toUserDto);

    }

    public void deleteById(Long userId) {
        userRepository.deleteById(userId);
    }

}

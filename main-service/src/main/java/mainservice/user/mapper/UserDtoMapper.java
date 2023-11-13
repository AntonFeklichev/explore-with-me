package mainservice.user.mapper;

import mainservice.user.dto.UserDto;
import mainservice.user.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserDtoMapper {

    UserDto toUserDto(User user);
}

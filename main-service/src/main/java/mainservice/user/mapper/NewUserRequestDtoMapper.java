package mainservice.user.mapper;

import mainservice.user.dto.NewUserRequestDto;
import mainservice.user.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface NewUserRequestDtoMapper {

    User toUser(NewUserRequestDto newUserRequestDto);

}

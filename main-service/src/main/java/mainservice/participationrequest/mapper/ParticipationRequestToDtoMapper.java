package mainservice.participationrequest.mapper;

import mainservice.participationrequest.dto.ParticipationRequestDto;
import mainservice.participationrequest.entity.ParticipationRequest;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ParticipationRequestToDtoMapper {

    ParticipationRequestDto toDto(ParticipationRequest participationRequest);

}

package mainservice.participationrequest.mapper;

import mainservice.participationrequest.dto.ParticipationRequestDto;
import mainservice.participationrequest.entity.ParticipationRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ParticipationRequestToDtoMapper {

    @Mapping(source = "event.id", target = "event")
    @Mapping(source = "requester.id", target = "requester")
    ParticipationRequestDto toDto(ParticipationRequest participationRequest);


}

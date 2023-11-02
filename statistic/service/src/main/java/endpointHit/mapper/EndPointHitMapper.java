package endpointHit.mapper;

import endPointHit.dto.EndPointHitDto;
import endpointHit.entity.EndPointHit;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface EndPointHitMapper {

    EndPointHitDto toEndPointHitDto(EndPointHit endPointHit);

    EndPointHit toEndPointHit(EndPointHitDto endPointHitDto);

}

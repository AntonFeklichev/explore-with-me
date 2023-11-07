package ewmstats.endpointhit.mapper;

import endpointhit.EndPointHitDto;
import ewmstats.endpointhit.entity.EndPointHit;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface EndPointHitMapper {

    EndPointHitDto toEndPointHitDto(EndPointHit endPointHit);

    EndPointHit toEndPointHit(EndPointHitDto endPointHitDto);

}

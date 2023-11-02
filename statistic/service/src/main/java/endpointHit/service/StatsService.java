package endpointHit.service;

import endPointHit.dto.EndPointHitDto;
import endpointHit.entity.EndPointHit;
import endpointHit.mapper.EndPointHitMapper;
import endpointHit.repository.StatsRepository;
import endpointHit.repository.projections.StatsProjection;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import stats.dto.StatsDto;
import stats.dto.StatsRequestDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StatsService {

    EndPointHitMapper endPointHitMapper;

    private final StatsRepository statsRepository;

    public void saveEndPointHit(EndPointHitDto endPointHitDto) {

        EndPointHit endPointHit = endPointHitMapper.toEndPointHit(endPointHitDto);
        statsRepository.save(endPointHit);
    }

    public List<StatsDto> getStats(StatsRequestDto statsRequestDto) {
        if (statsRequestDto.getUris().isEmpty()) {
            return getStatsByStartEnd(statsRequestDto);
        }
        return getStatsByStartEndUriUnique(statsRequestDto);
    }

    public List<StatsDto> getStatsByStartEnd(StatsRequestDto statsRequestDto) {
        if (statsRequestDto.getUnique()) {
            return findAllByStartEndUnique(statsRequestDto);
        }
        return findAllByStartEnd(statsRequestDto);
    }



    public List<StatsDto> getStatsByStartEndUriUnique(StatsRequestDto statsRequestDto) {
        if (statsRequestDto.getUnique()) {
            return findAllByStartEndUriUnique(statsRequestDto);
        }
        return findAllByStartEndUri(statsRequestDto);
    }


   private List<StatsDto> findAllByStartEnd(StatsRequestDto statsRequestDto) {
        return mapToStatsDto(
                statsRepository.findAllByStartEnd(statsRequestDto.getStart(),
                        statsRequestDto.getEnd()
                )
        );
    }

   private List<StatsDto> findAllByStartEndUnique(StatsRequestDto statsRequestDto) {
        return mapToStatsDto(
                statsRepository.findAllByStartEndUnique(statsRequestDto.getStart(),
                        statsRequestDto.getEnd()
                )
        );
    }

   private List<StatsDto> findAllByStartEndUri(StatsRequestDto statsRequestDto) {
        return mapToStatsDto(
                statsRepository.findAllByStartEndUri(statsRequestDto.getStart(),
                        statsRequestDto.getEnd(),
                        statsRequestDto.getUris()
                )
        );
    }

   private List<StatsDto> findAllByStartEndUriUnique(StatsRequestDto statsRequestDto) {
        return mapToStatsDto(
                statsRepository.findAllByStartEndUriUnique(statsRequestDto.getStart(),
                        statsRequestDto.getEnd(),
                        statsRequestDto.getUris()
                )
        );
    }


    private List<StatsDto> mapToStatsDto(List<StatsProjection> statsProjections) {

        return statsProjections.stream()
                .map(statsProj -> StatsDto.builder()
                        .app(statsProj.getApp())
                        .uri(statsProj.getUri())
                        .hits(statsProj.getHit())
                        .build())
                .collect(Collectors.toList());
    }
}

package ewmstats.endpointhit.repository;

import ewmstats.endpointhit.entity.EndPointHit;
import ewmstats.endpointhit.repository.projections.StatsProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface StatsRepository extends JpaRepository<EndPointHit, Long> {


    @Query("""
            SELECT
            COUNT (hit) AS hits,
            hit.app AS app,
            hit.uri AS uri
            FROM EndPointHit AS hit
            WHERE hit.timestamp BETWEEN ?1 AND ?2
            GROUP BY
            hit.app,
            hit.uri
            ORDER BY
            hits DESC
            """)
    List<StatsProjection> findAllByStartEnd(LocalDateTime start, LocalDateTime end);

    @Query("""
            SELECT
            COUNT(DISTINCT hit.ip) AS hits,
            hit.app AS app,
            hit.uri AS uri
            FROM EndPointHit AS hit
            WHERE hit.timestamp BETWEEN ?1 AND ?2
            GROUP BY
            hit.app,
            hit.uri
            ORDER BY
            hits DESC
            """)
    List<StatsProjection> findAllByStartEndUnique(LocalDateTime start, LocalDateTime end);

    @Query("""
            SELECT
            COUNT (hit) AS hits,
            hit.app AS app,
            hit.uri AS uri
            FROM EndPointHit AS hit
            WHERE (hit.timestamp BETWEEN ?1 AND ?2)
            AND hit.uri IN ?3
            GROUP BY
            hit.app,
            hit.uri
            ORDER BY
            hits DESC
            """
    )
    List<StatsProjection> findAllByStartEndUri(LocalDateTime start, LocalDateTime end,
                                               List<String> uris);


    @Query("""
            SELECT
            COUNT(DISTINCT hit.ip) AS hits,
            hit.app AS app,
            hit.uri AS uri
            FROM EndPointHit AS hit
            WHERE (hit.timestamp BETWEEN ?1 AND ?2)
            AND hit.uri IN ?3
            GROUP BY
            hit.app,
            hit.uri
            ORDER BY
            hits DESC
            """)
    List<StatsProjection> findAllByStartEndUriUnique(LocalDateTime start, LocalDateTime end,
                                                     List<String> uris);
}

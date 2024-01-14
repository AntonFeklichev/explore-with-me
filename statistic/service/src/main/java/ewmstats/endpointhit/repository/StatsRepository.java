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


    @Query("SELECT\n" +
           "COUNT (hit) AS hits,\n" +
           "hit.app AS app,\n" +
           "hit.uri AS uri\n" +
           "FROM EndPointHit AS hit\n" +
           "WHERE hit.timestamp BETWEEN ?1 AND ?2\n" +
           "GROUP BY\n" +
           "hit.app,\n" +
           "hit.uri\n" +
           "ORDER BY\n" +
           "hits DESC\n")
    List<StatsProjection> findAllByStartEnd(LocalDateTime start, LocalDateTime end);

    @Query("SELECT\n" +
           "COUNT(DISTINCT hit.ip) AS hits,\n" +
           "hit.app AS app,\n" +
           "hit.uri AS uri\n" +
           "FROM EndPointHit AS hit\n" +
           "WHERE hit.timestamp BETWEEN ?1 AND ?2\n" +
           "GROUP BY\n" +
           "hit.app,\n" +
           "hit.uri\n" +
           "ORDER BY\n" +
           "hits DESC\n")
    List<StatsProjection> findAllByStartEndUnique(LocalDateTime start, LocalDateTime end);

    @Query("SELECT\n" +
           "COUNT (hit) AS hits,\n" +
           "hit.app AS app,\n" +
           "hit.uri AS uri\n" +
           "FROM EndPointHit AS hit\n" +
           "WHERE (hit.timestamp BETWEEN ?1 AND ?2)\n" +
           "AND hit.uri IN ?3\n" +
           "GROUP BY\n" +
           "hit.app,\n" +
           "hit.uri\n" +
           "ORDER BY\n" +
           "hits DESC\n"
    )
    List<StatsProjection> findAllByStartEndUri(LocalDateTime start, LocalDateTime end,
                                               List<String> uris);


    @Query("SELECT\n" +
           "COUNT(DISTINCT hit.ip) AS hits,\n" +
           "hit.app AS app,\n" +
           "hit.uri AS uri\n" +
           "FROM EndPointHit AS hit\n" +
           "WHERE (hit.timestamp BETWEEN ?1 AND ?2)\n" +
           "AND hit.uri IN ?3\n" +
           "GROUP BY\n" +
           "hit.app,\n" +
           "hit.uri\n" +
           "ORDER BY\n" +
           "hits DESC\n")
    List<StatsProjection> findAllByStartEndUriUnique(LocalDateTime start, LocalDateTime end,
                                                     List<String> uris);


    @Query("SELECT COUNT(h.ip) " +
           "FROM EndPointHit h " +
           "WHERE h.ip = ?1 " +
           "AND h.uri = ?2")
    Long getHitCountByIpAndUri(String ip, String uri);
}

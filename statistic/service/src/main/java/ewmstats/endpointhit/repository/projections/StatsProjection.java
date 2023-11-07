package ewmstats.endpointhit.repository.projections;

public interface StatsProjection {

    String getApp();

    String getUri();

    Long getHits();

}

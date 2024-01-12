package statsclient;

import base.BaseClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class StatsClient extends BaseClient {

    private static final String apiPref = "/stats";

    public StatsClient(@Value("stats.server.URL")
                       String serverUrl,
                       RestTemplateBuilder restTemplate) {
        super(restTemplate
                .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + apiPref))
                .requestFactory(HttpComponentsClientHttpRequestFactory.class)
                .build());
    }

    protected ResponseEntity<Object> getStats(LocalDateTime start,
                                              LocalDateTime end,
                                              List<String> uris,
                                              Boolean unique) {

        Map<String, Object> params = Map.of("start", start,
                "end", end,
                "uris", uris,
                "unique", unique);


        String uriTemplate = "/stats?start={start}&end={end}&uris={uris}&unique={unique}";

        return get(uriTemplate, params);
    }
}


package endpointhitclient;

import base.BaseClient;
import endpointhit.EndPointHitDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.util.DefaultUriBuilderFactory;

@Component
public class EndPointHitClient extends BaseClient {

    private static final String apiPref = "/hit";

    @Autowired
    public EndPointHitClient(@Value("stats.server.URL")
                             String serverUrl,
                             RestTemplateBuilder restTemplate) {
        super(restTemplate
                .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + apiPref))
                .build());
    }

    public void saveEndPointHit(EndPointHitDto endPointHitDto) {

        post(endPointHitDto, "");
    }

}

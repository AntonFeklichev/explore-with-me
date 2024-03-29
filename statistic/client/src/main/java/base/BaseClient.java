package base;

import org.springframework.http.*;
import org.springframework.lang.Nullable;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

public class BaseClient {

    private final RestTemplate restTemplate;

    public BaseClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    protected <T> void post(T body, String path) {
        makeAndSendRequest(HttpMethod.POST, path, null, body);
    }

    protected <T> ResponseEntity<Object> get(String path, Map<String, Object> params) {
        return makeAndSendRequest(HttpMethod.GET, path, params, null);
    }



    private <T> ResponseEntity<Object> makeAndSendRequest(HttpMethod method,
                                                          String path,
                                                          @Nullable
                                                          Map<String, Object> parameters,
                                                          @Nullable
                                                          T body) {

        HttpEntity<T> requestEntity = new HttpEntity<>(body, defaultheaders());
        ResponseEntity<Object> statsServerResponse;
        try {
            if (parameters != null) {
                statsServerResponse = restTemplate
                        .exchange(path, method, requestEntity, Object.class, parameters);
            } else {
                statsServerResponse = restTemplate
                        .exchange(path, method, requestEntity, Object.class);
            }
        } catch (HttpStatusCodeException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsByteArray());
        }
        return statsServerResponse;
    }


    private HttpHeaders defaultheaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        return headers;
    }

}


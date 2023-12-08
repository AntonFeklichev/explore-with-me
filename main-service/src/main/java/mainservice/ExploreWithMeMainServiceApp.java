package mainservice;

import endpointhitclient.EndPointHitClient;
import mainservice.event.repository.CustomEventRepositoryImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = CustomEventRepositoryImpl.class)
public class ExploreWithMeMainServiceApp {
    public static void main(String[] Arg) {
        SpringApplication.run(ExploreWithMeMainServiceApp.class);
    }

    @Bean
    EndPointHitClient doBeanByEndPointHitClient(@Value("stats.server.URL")
                                                String serverUrl,
                                                RestTemplateBuilder restTemplate) {
        return new EndPointHitClient(serverUrl, restTemplate);
    }

}

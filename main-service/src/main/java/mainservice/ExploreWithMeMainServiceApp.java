package mainservice;

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import endpointhitclient.EndPointHitClient;
import mainservice.event.repository.CustomEventRepositoryImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = CustomEventRepositoryImpl.class)
public class ExploreWithMeMainServiceApp {
    public static void main(String[] Arg) {
        SpringApplication.run(ExploreWithMeMainServiceApp.class);
    }

    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    @Bean
    EndPointHitClient doBeanByEndPointHitClient(@Value("${stats.server.URL}")
                                                String serverUrl,
                                                RestTemplateBuilder restTemplate) {
        return new EndPointHitClient(serverUrl, restTemplate);
    }


    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jsonCustomizer() {
        return builder ->
                builder.simpleDateFormat(DATE_TIME_FORMAT)
                        .deserializerByType(
                                LocalDateTime.class,
                                new LocalDateTimeDeserializer(
                                        DateTimeFormatter.ofPattern(DATE_TIME_FORMAT)
                                )
                        )
                        .serializerByType(
                                LocalDateTime.class,
                                new LocalDateTimeSerializer(
                                        DateTimeFormatter.ofPattern(DATE_TIME_FORMAT)
                                )
                        );
    }



}

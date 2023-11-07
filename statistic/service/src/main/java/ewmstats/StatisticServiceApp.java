package ewmstats;


import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@SpringBootApplication
public class StatisticServiceApp {
    public static void main(String[] arg) {
        SpringApplication.run(StatisticServiceApp.class);
    }

    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

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

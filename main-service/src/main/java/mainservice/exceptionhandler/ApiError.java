package mainservice.exceptionhandler;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class ApiError {
    private final List<String> errors;
    private final String message;
    private final String reason;
    private final HttpStatus status;
    private final LocalDateTime timestamp;
}

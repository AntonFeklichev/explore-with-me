package mainservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class EventPublishedExceptions extends RuntimeException {
    public EventPublishedExceptions(String message) {
        super(message);
    }
}

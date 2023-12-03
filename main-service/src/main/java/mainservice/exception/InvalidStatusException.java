package mainservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class InvalidStatusException extends RuntimeException{
    public InvalidStatusException(String message) {
        super(message);
    }
}

package mainservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class InvalidStateActionException extends RuntimeException{
    public InvalidStateActionException(String message) {
        super(message);
    }
}

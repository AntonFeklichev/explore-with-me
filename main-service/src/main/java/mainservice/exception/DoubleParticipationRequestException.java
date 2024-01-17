package mainservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT)
public class DoubleParticipationRequestException extends RuntimeException {
    public DoubleParticipationRequestException(String message) {
        super(message);
    }
}

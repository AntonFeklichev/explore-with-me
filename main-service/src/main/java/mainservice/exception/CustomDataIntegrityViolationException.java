package mainservice.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class CustomDataIntegrityViolationException extends DataIntegrityViolationException {
    public CustomDataIntegrityViolationException(String msg) {
        super(msg);
    }
}

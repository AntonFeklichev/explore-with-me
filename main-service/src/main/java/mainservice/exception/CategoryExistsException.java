package mainservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT)
public class CategoryExistsException extends RuntimeException{
    public CategoryExistsException(String message) {
        super(message);
    }
}

package mainservice.exceptionhandler;

import mainservice.exception.CustomDataIntegrityViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice(basePackages = "mainservice")
public class DefaultExceptionHandler {
    @ExceptionHandler
    @ResponseStatus(code = HttpStatus.CONFLICT)
    public ApiError handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        return ApiError.builder()
                .reason("Duplicate key value violates unique constraint.")
                .timestamp(LocalDateTime.now())
                .message(e.getMessage())
                .status(HttpStatus.CONFLICT)
                .build();
    }
}

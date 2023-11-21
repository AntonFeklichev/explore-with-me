package mainservice.exception;

public class InvalidStateActionException extends RuntimeException{
    public InvalidStateActionException(String message) {
        super(message);
    }
}

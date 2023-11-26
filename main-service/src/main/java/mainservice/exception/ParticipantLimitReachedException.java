package mainservice.exception;

public class ParticipantLimitReachedException extends RuntimeException{
    public ParticipantLimitReachedException(String message) {
        super(message);
    }
}

package mainservice.exception;

public class CompilationNotFoundException extends RuntimeException{
    public CompilationNotFoundException(String message) {
        super(message);
    }
}

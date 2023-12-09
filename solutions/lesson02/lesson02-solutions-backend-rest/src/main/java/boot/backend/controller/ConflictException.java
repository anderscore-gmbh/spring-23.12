package boot.backend.controller;

public class ConflictException extends RuntimeException {

    public ConflictException(String message) {
        super(message);
    }
}

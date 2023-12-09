package boot.backend.exception;

public class NotFoundException extends RuntimeException {

    public NotFoundException(long id) {
        super("There is no task with id = " + id);
    }
}
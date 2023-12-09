package boot.backend.controller;

public class NotFoundException extends RuntimeException {

    public NotFoundException(long id) {
        super("There is no task with id = " + id);
    }
}

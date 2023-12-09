package boot.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason="There is no such object")
public class NotFoundException extends RuntimeException {

    public NotFoundException(long id) {
        super("There is no task with id = " + id);
    }
}

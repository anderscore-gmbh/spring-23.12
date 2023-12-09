package boot.backend.controller;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    protected ResponseEntity<Object> handleNotFound(final RuntimeException ex, final WebRequest request) {
        logger.warn("Not found: " + ex.getMessage());

        HttpStatus status = HttpStatus.NOT_FOUND;
        RestApiError error = error(status, ex);
        return handleExceptionInternal(ex, error, new HttpHeaders(), status, request);
    }

    @ExceptionHandler({ ConflictException.class, DataAccessException.class })
    protected ResponseEntity<Object> handleConflict(final RuntimeException ex, final WebRequest request) {
        logger.warn("Conflict", ex);

        HttpStatus status = HttpStatus.CONFLICT;
        RestApiError error = error(status, ex);
        return handleExceptionInternal(ex, error, new HttpHeaders(), status, request);
    }

    @ExceptionHandler({ NullPointerException.class, IllegalArgumentException.class, IllegalStateException.class })
    protected ResponseEntity<Object> handleIntenalServerError(final RuntimeException ex, final WebRequest request) {
        logger.error("Internal Server Error", ex);

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        RestApiError error = error(status, ex);
        return handleExceptionInternal(ex, error, new HttpHeaders(), status, request);
    }
    
    private RestApiError error(HttpStatus httpStatus, Exception ex) {
        String message = ex.getMessage() == null ? ex.getClass().getSimpleName() : ex.getMessage();
        return new RestApiError(httpStatus.value(), message);
    }
}

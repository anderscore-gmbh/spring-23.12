package hello;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

// tag::code[]
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(WorstException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public RestApiError handleWorst(WorstException ex) {
        logger.warn("Worst: " + ex.getMessage());
        return error(HttpStatus.CONFLICT, ex);
    }

    @ExceptionHandler({ NullPointerException.class, IllegalArgumentException.class, IllegalStateException.class })
    public ResponseEntity<Object> handleIntenalServerError(RuntimeException ex, WebRequest request) {
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
// end::code[]
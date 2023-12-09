package hello;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// tag::code[]
@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason="Sample Exception")
public class BadException extends RuntimeException {
}
// end::code[]
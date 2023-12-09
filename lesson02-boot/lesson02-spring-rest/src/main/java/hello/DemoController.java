package hello;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
public class DemoController {

    // tag::uri[]
    @GetMapping("/uri/{id}")
    public String uri(@PathVariable int id, UriComponentsBuilder uriBuilder) {
        String uri = uriBuilder.path("/uri/{id}").build().expand(id).encode().toUriString();
        return uri;
    }
    // end::uri[]

    @GetMapping(path="/json", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public DemoDto hello(String value) {
        return new DemoDto(value);
    }

    @GetMapping("/bad")
    public void bad() {
        throw new BadException();
    }

    @GetMapping("/worse")
    public void worse(String name) {
        throw new WorseException("Worse aufgerufen mit name=" + name + ".");
    }

    @GetMapping("/worst")
    public void worst(String name) {
        throw new WorstException("Worst aufgerufen mit name=" + name + ".");
    }

    @GetMapping("/easy")
    public void easy(String name) {
        // tag::easy[]
        throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Easy aufgrufen mit name=" + name);
        // end::easy[]
    }

    // tag::worse[]
    @ExceptionHandler({ WorseException.class })
    public ResponseEntity<RestApiError> handleWorse(WorseException ex) {
        HttpStatus status = HttpStatus.GONE;
        RestApiError body = new RestApiError(status.value(), ex.getMessage());
        return ResponseEntity.status(status).body(body);
    }
    // end::worse[]

    @GetMapping("/npe")
    public void npe() {
        throw new NullPointerException();
    }
}
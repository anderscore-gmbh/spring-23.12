package hello;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// tag::code[]
@RestController
public class HelloController {

    @GetMapping(path="/hello", produces = "text/html")
    public String hello(@RequestParam String name) {
        return "Hello " + name + "!";
    }
}
// end::code[]
package hello;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CheckController {

    @GetMapping(path="/check")
    public String hello() {
        return "checkview";
    }
}

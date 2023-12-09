package com.anderscore.example.ContainerExample;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class HelloController {

    @GetMapping(path="/hello", produces = "text/html")
    public String hello() {
        return "Hello Kubernetes! Ich bin " + Optional.ofNullable(System.getenv("HOSTNAME")).get();
    }
}

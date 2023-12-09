package boot.fragments.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

// tag::app[]
@SpringBootApplication
public class MyBootWebApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(MyBootWebApplication.class); // <1>
    }

    public static void main(String[] args) { // <2>
        SpringApplication.run(MyBootWebApplication.class, args);
    }
}
// end::app[]

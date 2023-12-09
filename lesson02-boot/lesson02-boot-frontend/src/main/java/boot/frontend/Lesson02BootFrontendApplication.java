package boot.frontend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class Lesson02BootFrontendApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(Lesson02BootFrontendApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(Lesson02BootFrontendApplication.class, args);
    }
}

package hello;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// tag::code[]
@Configuration
@ComponentScan
@EnableWebMvc // <1>
public class HelloConfig implements WebMvcConfigurer { // <2>
    
}
// end::code[]

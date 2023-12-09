package boot.fragments.application;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

// tag::app[]
@SpringBootApplication
public class MyBootApplication {
    private static final Logger log = LogManager.getLogger(MyBootApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(MyBootApplication.class, args);
    }
    // end::app[]

    // tag::CommandLineRunner[]
    @Bean
    CommandLineRunner commandLineArgsInitializer() {
        return new CommandLineRunner() {
            
            @Override
            public void run(String... args) throws Exception {
                log.info(() -> "Command-Line: " + Arrays.toString(args));
            }
        };
    }
    // end::CommandLineRunner[]
    
    // tag::ApplicationRunner[]
    @Bean
    ApplicationRunner interpretedArgsInitializer() {
        return new ApplicationRunner() {
            
            @Override
            public void run(ApplicationArguments args) throws Exception {
                log.info(() -> "Options: " + args.getOptionNames().stream()
                        .map(name -> name + ": " + args.getOptionValues(name))
                        .collect(Collectors.joining(", ")));
            }
        };
    }
    // end::ApplicationRunner[]
    
    // tag::app[]
}
// end::app[]

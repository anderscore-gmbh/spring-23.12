package boot.fragments.application;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

// tag::app[]
@SpringBootApplication
public class MyCustomizedBootApplication {
    private static final Logger log = LogManager.getLogger(MyCustomizedBootApplication.class);

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(MyCustomizedBootApplication.class);
        customize(application);
        application.run();
    }

    private static void customize(SpringApplication application) {
        application.addListeners(new ApplicationListener<ApplicationEvent>() {

            @Override
            public void onApplicationEvent(ApplicationEvent event) {
                log.info(() -> "Application Event: " + event);
            }
        });
    }
}
// end::app[]

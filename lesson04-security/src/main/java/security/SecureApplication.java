package security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// Für OAuth2 ausführen mit: -Dspring.profiles.active=oauth2 ... (siehe google.txt)
@SpringBootApplication
public class SecureApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecureApplication.class, args);
    }
}

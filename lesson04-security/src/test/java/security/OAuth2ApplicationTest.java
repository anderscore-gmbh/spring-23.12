package security;


import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("oauth2")
public class OAuth2ApplicationTest {

    @Test
    public void contextLoads() {
    }

}

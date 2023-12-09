package security.usermgmt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Component
public class SetupWebUsersCommandLineRunner implements CommandLineRunner {

    @Autowired
    private WebUserService service;

    @Autowired(required = false)
    private PasswordEncoder encoder;

    @Override
    public void run(String... args) throws Exception {
        if (encoder == null) {
            encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        }
        List<WebUser> users =
                Arrays.asList(
                        createWebUser("hugo", "pwd123", "USER"),
                        createWebUser("fritz", "secret123", "ADMIN"),
                        createWebUser("egon", "geheim", "USER", "ADMIN")
                );
        service.saveUsers(users);
    }

    private WebUser createWebUser(String username, String password, String... roles) {
        WebUser user = new WebUser();
        user.setUsername(username);
        user.setPassword(encoder.encode(password));
        user.setRoles(new HashSet<>(Arrays.asList(roles)));
        return user;
    }
}

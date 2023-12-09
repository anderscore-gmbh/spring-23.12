package security.fragments;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class AuthenticationExampleTest {
    static final List<GrantedAuthority> AUTHORITIES = new ArrayList<GrantedAuthority>();

    static {
        AUTHORITIES.add(new SimpleGrantedAuthority("ROLE_USER"));
    }

   	// @formatter:off
    // tag::authenticate[]
    class SampleAuthenticationManager implements AuthenticationManager {

        public Authentication authenticate(Authentication auth)
                throws AuthenticationException {
            if (auth.getName().equals(auth.getCredentials())) {
                return new UsernamePasswordAuthenticationToken(auth.getName(),
                        auth.getCredentials(), AUTHORITIES);
            }
            throw new BadCredentialsException("Bad Credentials");
        }
    }
    // end::authenticate[]
   	// @formatter:on

    private AuthenticationManager am = new SampleAuthenticationManager();

    @Test
    void testSuccessfulLogin() {
        login("blub", "blub");
        Assertions.assertThat(getUsername()).isEqualTo("blub");
        Assertions.assertThat(hasRole("ROLE_USER")).isTrue();
    }

    @Test
    void testLoginFailes() {
        assertThrows(BadCredentialsException.class,
                () -> login("blub", "bla"));
    }

    private boolean hasRole(String role) {
        Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        boolean contained = authorities.contains(new SimpleGrantedAuthority(role));
        return contained;
    }

    private String getUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        return username;
    }

    private void login(String username, String password) {
        // tag::login[]
        Authentication request = new UsernamePasswordAuthenticationToken(username, password);
        Authentication result = am.authenticate(request);
        SecurityContextHolder.getContext().setAuthentication(result);
        // end::login[]
    }
}

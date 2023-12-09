package security.fragments;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.*;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringJUnitConfig
public class UserDetailsServiceTest {

    @Configuration
    static class Config {

        @Bean
        UserDetailsService userDetailsService() {
            InMemoryUserDetailsManager service = new InMemoryUserDetailsManager(
                    createUser("Hugo", "pwd123", "ROLE_USER"),
                    createUser("Fritz", "secret123", "ROLE_USER", "ROLE_ADMIN")
            );
            return service;
        }

        private User createUser(String username, String password, String... roles) {
            List<SimpleGrantedAuthority> roleList = new ArrayList<>(roles.length);
            for (String role : roles) {
                roleList.add(new SimpleGrantedAuthority(role));
            }
            PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
            String encodedPassword = passwordEncoder.encode(password);
            User user = new User(username, encodedPassword, roleList);
            return user;
        }

        @Bean
        DaoAuthenticationProvider daoAuthenticationProvider() {
            PasswordEncoder en;
            DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
            provider.setUserDetailsService(userDetailsService());
            return provider;
        }

        @Bean
        ProviderManager providerManager(List<AuthenticationProvider> providers) {
            return new ProviderManager(providers);
        }
    }

    @Autowired
    private AuthenticationManager am;

    @Test
    void testUserLogin() {
        login("Hugo", "pwd123");
        Assertions.assertThat(getUsername()).isEqualTo("Hugo");
        Assertions.assertThat(hasRole("ROLE_USER")).isTrue();
        Assertions.assertThat(hasRole("ROLE_ADMIN")).isFalse();
    }

    @Test
    void testAdminLogin() {
        login("Fritz", "secret123");
        Assertions.assertThat(getUsername()).isEqualTo("Fritz");
        Assertions.assertThat(hasRole("ROLE_USER")).isTrue();
        Assertions.assertThat(hasRole("ROLE_ADMIN")).isTrue();
    }

    @Test
    void testLoginFailes() {
        assertThrows(BadCredentialsException.class,
                () -> login("Fritz", "wrong"));
    }

    private boolean hasRole(String role) {
        Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        boolean contained = authorities.contains(new SimpleGrantedAuthority(role));
        return contained;
    }

   	// @formatter:off
    // tag::getUsername[]
    private String getUsername() {
        Object principal = SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();

        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        return username;
    }
    // end::getUsername[]
   	// @formatter:on

    private void login(String username, String password) {
        Authentication request = new UsernamePasswordAuthenticationToken(username, password);
        Authentication result = am.authenticate(request);
        SecurityContextHolder.getContext().setAuthentication(result);
    }
}

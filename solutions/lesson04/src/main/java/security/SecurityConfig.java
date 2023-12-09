package security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import security.usermgmt.WebUserService;

@Profile("default")
@Configuration
@EnableMethodSecurity
public class SecurityConfig {


    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails user = User.withDefaultPasswordEncoder()
                .username("user").password("user").roles("ROLE")
                .build();
        UserDetails admin = User.withDefaultPasswordEncoder()
                .username("admin").password("secret123").roles("ADMIN").build();
        return new InMemoryUserDetailsManager(user, admin);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((authz) -> authz
                .requestMatchers("/", "/webjars/**", "/error/**", "/anonymous").permitAll()
                .requestMatchers("/admin/**").hasRole("ADMIN").anyRequest().authenticated());
        http.formLogin((formLogin) ->
                formLogin.loginPage("/login")
                        .permitAll()
                        .defaultSuccessUrl("/authenticated")
        );
        http.logout((customizer) ->
                customizer.permitAll().deleteCookies("JSESSIONID")
        );
        return http.build();
    }
}

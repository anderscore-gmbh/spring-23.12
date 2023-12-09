package security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Profile("oauth2")
@Configuration
@EnableMethodSecurity()
public class OAuth2Config {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((authz) -> authz
                .requestMatchers("/", "/webjars/**", "/error/**", "/anonymous").permitAll()
                .requestMatchers("/admin/**").hasRole("ADMIN").anyRequest().authenticated());
        http.oauth2Login((oauth) ->
                oauth.permitAll().defaultSuccessUrl("/authenticated")
        );
        http.logout((customizer) ->
                customizer.permitAll().deleteCookies("JSESSIONID")
        );

        return http.build();
    }
}

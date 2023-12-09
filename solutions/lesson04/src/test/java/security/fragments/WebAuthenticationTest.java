package security.fragments;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@SpringJUnitWebConfig
public class WebAuthenticationTest {

    @Configuration
    @EnableWebMvc
    @Import(SecurityConfig.class)
    static class WebConfig implements WebMvcConfigurer {

        @Bean
        SampleController sampleController() {
            return new SampleController();
        }
    }

    @Configuration
    @EnableWebSecurity
    static class SecurityConfig  {

    	// @formatter:off
        // tag::http-sec[]
        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
            http.authorizeHttpRequests((authz) -> authz
                    .requestMatchers("/", "/login", "/unauthorized").permitAll()
                    // alternativ: hasRole("ADMIN")
                    .requestMatchers("/admin/**").hasRole("ADMIN").anyRequest().authenticated());
            http.formLogin((formLogin) -> formLogin
                    .loginPage("/login") // Login-Seite
                    .defaultSuccessUrl("/secured") // wenn kein Referer gesetzt war
                    .failureUrl("/login?error=bad_credentials")); // falls Login fehlgeschlagen
            http.logout((logout) -> logout // bei Logout
                            .deleteCookies("JSESSIONID") // cookie loeschen
                            .logoutUrl("/logout") // Logout-Seite
                    .logoutSuccessUrl("/")  // nach dem Logout
            );
            return http.build();
        }
        // end::http-sec[]


        @Bean
        public InMemoryUserDetailsManager userDetailsService() {
            UserDetails user = User.withUsername("hugo").password("{noop}pwd123").roles("USER").build();
            UserDetails admin = User.withUsername("fritz").password("{noop}secret123").roles("USER", "ADMIN").build();
            return new InMemoryUserDetailsManager(user, admin);
        }
    	// @formatter:on

    }

    @RestController
    static class SampleController {

        @GetMapping("/unauthorized")
        public String greeting() {
            return "Hello World!";
        }

        @GetMapping("/secured")
        public String securedGreeting() {
            return "It's a Save World!";
        }

        @GetMapping("/admin/greeting")
        public String adminGreeting() {
            return "It's the Admin World!";
        }
    }

    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
    	// @formatter:off
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(wac)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    	// @formatter:on
    }

    @Test
    void testOpenAccess() throws Exception {
    	// @formatter:off
        mockMvc.perform(MockMvcRequestBuilders.get("/unauthorized")
                .accept(MediaType.TEXT_PLAIN))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN))
                .andExpect(MockMvcResultMatchers.content().string("Hello World!"));
    	// @formatter:on
    }

    @Test
    void testAccessProhibited() throws Exception {
    	// @formatter:off
        mockMvc.perform(MockMvcRequestBuilders.get("/secured")
                .accept(MediaType.TEXT_PLAIN))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.redirectedUrlPattern("**/login"));
    	// @formatter:on
    }

    @Test
    void testLogin() throws Exception {
    	// @formatter:off
        mockMvc.perform(MockMvcRequestBuilders.post("/login")
                .param("username", "Hugo")
                .param("password", "pwd123")
                .with(csrf())
                .accept(MediaType.TEXT_PLAIN))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/secured"));
    	// @formatter:on
    }

    @Test
    void testLoginFailed() throws Exception {
    	// @formatter:off
        mockMvc.perform(MockMvcRequestBuilders.post("/login")
                .param("username", "Hugo")
                .param("password", "wrongpwd")
                .with(csrf())
                .accept(MediaType.TEXT_PLAIN))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/login?error=bad_credentials"));
    	// @formatter:on
    }
}

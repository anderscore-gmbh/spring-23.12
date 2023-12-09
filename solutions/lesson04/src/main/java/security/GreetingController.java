package security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GreetingController {
    private static final Logger log = LoggerFactory.getLogger(GreetingController.class);

    @GetMapping("/anonymous")
    public String greetAnonymous(Model model) {
        model.addAttribute("name", "Anonymous User");
        return "greeting";
    }

    @GetMapping("/authenticated")
    public String greetAuthenticated(Model model) {
        String username = getCurrentUsername();
        model.addAttribute("name", username);
        return "greeting";
    }

    @GetMapping("/admin/greeting")
    public String greetAdmin(Model model) {
        String username = getCurrentUsername();
        model.addAttribute("name", username);
        return "greeting";
    }

    @GetMapping("/user/greeting")
    @PreAuthorize("hasRole('USER')")
    public String greetUsersOnly(Model model) {
        String username = getCurrentUsername();
        model.addAttribute("name", username);
        return "greeting";
    }

    private String getCurrentUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            UserDetails user = (UserDetails) principal;
            username = user.getUsername();
        } else {
            username = String.valueOf(principal);
        }
        return username;
    }

    @GetMapping("/login")
    public String showLoginPage() {
        log.info("Showing login-page...");
        return "login/login";
    }
}

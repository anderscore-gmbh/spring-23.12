package security.usermgmt;

import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Collection;

public interface WebUserService extends UserDetailsService {
    void saveUsers(Collection<WebUser> users);
}

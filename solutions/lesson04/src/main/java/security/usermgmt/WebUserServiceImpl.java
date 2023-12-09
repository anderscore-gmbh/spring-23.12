package security.usermgmt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.Collection;

@Service
@Transactional
public class WebUserServiceImpl implements WebUserService {

    @Autowired
    private WebUserRepository webUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        WebUser user = webUserRepository.findWebUserByUsername(username);
        return user;
    }

    @Override
    public void saveUsers(Collection<WebUser> users) {
        webUserRepository.saveAll(users);
    }
}

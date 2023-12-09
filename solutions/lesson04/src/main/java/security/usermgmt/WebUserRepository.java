package security.usermgmt;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface WebUserRepository extends JpaRepository<WebUser, Long> {

    @Query("from WebUser w left join fetch w.roles where w.username = :username")
    WebUser findWebUserByUsername(@Param("username") String username);
}

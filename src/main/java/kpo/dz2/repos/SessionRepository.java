package kpo.dz2.repos;

import kpo.dz2.domain.Session;
import kpo.dz2.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface SessionRepository extends JpaRepository<Session, Long> {

    Session findFirstByUser(User user);

}

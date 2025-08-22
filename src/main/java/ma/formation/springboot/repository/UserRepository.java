package ma.formation.springboot.repository;

import ma.formation.springboot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsernameAndPassword(String username, String password);
    User findByUsername(String username);
    boolean existsByUsername(String username);

    long countByUsername(String username);
}
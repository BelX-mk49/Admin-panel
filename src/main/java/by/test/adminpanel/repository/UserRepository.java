package by.test.adminpanel.repository;

import by.test.adminpanel.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String login);

    Optional<User> findByUserId(Long userId);
}

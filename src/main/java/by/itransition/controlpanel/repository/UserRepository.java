package by.itransition.controlpanel.repository;

import by.itransition.controlpanel.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String login);

    User findByActive(boolean active);

    Optional<User> findByUserId(Long userId);
}

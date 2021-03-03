package by.test.adminpanel.repository;

import by.test.adminpanel.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String login);
    Optional<User> findByUserId(Long userId);
}

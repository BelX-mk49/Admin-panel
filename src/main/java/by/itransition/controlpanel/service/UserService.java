package by.itransition.controlpanel.service;

import by.itransition.controlpanel.entity.User;
import by.itransition.controlpanel.repository.UserRepository;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

@Service
@Transactional
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws LockedException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new LockedException("User not found");
        }
        if (!user.isActive()) {
            throw new LockedException("user is not activated");
        }
        return user;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public void saveUser(User user, String username, Map<String, String> form) {
        user.setUsername(username);

        userRepository.save(user);
    }

    public boolean addUser(User user) {
        if (!validationUser(user)) {
            user.setActive(false);
            user.setPassword(passwordEncoder.encode(user.getPassword()));

            userRepository.save(user);
            return TRUE;
        } else {
            return FALSE;
        }
    }

    public boolean banUser(Long userId) {
        User user = userRepository.findByUserId(userId).orElseThrow();
        if (validationUser(user)) {
            user.setActive(false);
            return TRUE;
        }else {
            return FALSE;
        }
    }

    private boolean validationUser(User user) {
        return userRepository.findByUsername(user.getUsername()) != null;
    }

    public boolean activateUser(boolean active) {
        User user = userRepository.findByActive(active);

        if (user != null) {
            user.setActive(true);
            userRepository.save(user);
            return TRUE;
        } else {
            return FALSE;
        }
    }

    public boolean isConfirmEmpty(@RequestParam("password2") String passwordConfirm) {
        return StringUtils.isEmpty(passwordConfirm);
    }

    public boolean isPasswordDifferent(@RequestParam("password2") String passwordConfirm, @AuthenticationPrincipal User user) {
        return user.getPassword() != null && !user.getPassword().equals(passwordConfirm);
    }

    public void passwordConfirmEmpty(@RequestParam("password2") String passwordConfirm, Model model) {
        if (isConfirmEmpty(passwordConfirm)){
            model.addAttribute("password2Error", "Password confirmation can't be empty");
        }
    }
}

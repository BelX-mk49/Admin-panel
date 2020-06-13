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
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

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
            throw new LockedException("user banned");
        }
        return user;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public boolean addUser(User user) {
        if (!validationUser(user)) {
            Calendar calendar = Calendar.getInstance();
            DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
            user.setActive(true);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRegistrationDate(df.format(calendar.getTime()));
            user.setLastLoginDate(df.format(calendar.getTime()));
            userRepository.save(user);
            return TRUE;
        } else {
            return FALSE;
        }
    }

    public void banUser(Long userId) {
        User user = userRepository.findByUserId(userId).orElseThrow();
        if (validationUser(user)) {
            user.setActive(false);
        }
    }

    public void updateLoginDate(String date, String username) {
        userRepository.findByUsername(username).setLastLoginDate(date);
    }

    private boolean validationUser(User user) {
        return userRepository.findByUsername(user.getUsername()) != null;
    }

    public boolean isConfirmEmpty(@RequestParam("password2") String passwordConfirm) {
        return StringUtils.isEmpty(passwordConfirm);
    }

    public boolean isPasswordDifferent(@RequestParam("password2") String passwordConfirm, @AuthenticationPrincipal User user) {
        return user.getPassword() != null && !user.getPassword().equals(passwordConfirm);
    }
}

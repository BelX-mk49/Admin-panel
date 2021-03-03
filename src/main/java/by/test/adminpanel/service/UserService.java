package by.test.adminpanel.service;

import by.test.adminpanel.entity.User;
import by.test.adminpanel.repository.UserRepository;
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
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

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

    public String block(String[] userId, User currentUser, String redirect) {
        for (Long id : arrayToList(userId)){
            if (getCurrentUserId(currentUser).equals(id)) {
                redirect = "redirect:/login?logout";
            }
            getUser(id).setActive(false);
        }
        return redirect;
    }

    public String unblock(String[] userId, User currentUser, String redirect) {
        for (Long id : arrayToList(userId)){
            if (getCurrentUserId(currentUser).equals(id)) {
                redirect = "redirect:/";
            }
            getUser(id).setActive(true);
        }
        return redirect;
    }

    private User getUser(Long id) {
        return userRepository.findByUserId(id).orElseThrow();
    }

    public String delete(String[] userId, User currentUser, String redirect) {
        for (Long id : arrayToList(userId)){
            if (getCurrentUserId(currentUser).equals(id)) {
                redirect = "redirect:/login?logout";
            }
            userRepository.delete(getUser(id));
        }
        return redirect;
    }


    private Long getCurrentUserId(User currentUser) {
        return currentUser.getUserId();
    }

    private List<Long> arrayToList(String[] userId) {
        return Arrays.stream(userId).map(Long::valueOf).collect(Collectors.toList());
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

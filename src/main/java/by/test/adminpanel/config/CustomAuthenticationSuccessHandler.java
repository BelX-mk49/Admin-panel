package by.test.adminpanel.config;

import by.test.adminpanel.entity.User;
import by.test.adminpanel.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@Component
public class CustomAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private final UserRepository userRepository;

    public CustomAuthenticationSuccessHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest req, HttpServletResponse res, Authentication auth) throws IOException {
        clearAuthenticationAttributes(req);
        redirectStrategy.sendRedirect(req, res, "/");
        Calendar date = Calendar.getInstance();
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        User user = userRepository.findByUsername(auth.getName());
        user.setLastLoginDate(df.format(date.getTime()));
        userRepository.save(user);
    }
}

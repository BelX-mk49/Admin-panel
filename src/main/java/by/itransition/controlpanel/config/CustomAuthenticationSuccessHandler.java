package by.itransition.controlpanel.config;

import by.itransition.controlpanel.repository.UserRepository;
import by.itransition.controlpanel.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CustomAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private final UserRepository userRepository;

    public CustomAuthenticationSuccessHandler( UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest req, HttpServletResponse res, Authentication auth){
        Calendar date = Calendar.getInstance();
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        userRepository.findByUsername(auth.getName()).setLastLoginDate(df.format(date.getTime()));
    }
}

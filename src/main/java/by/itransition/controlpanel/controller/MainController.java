package by.itransition.controlpanel.controller;

import by.itransition.controlpanel.entity.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String greeting(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("user", user);
        return "greeting";
    }

    @GetMapping("/main")
    public String main(Model model) {

        return "main";
    }

}

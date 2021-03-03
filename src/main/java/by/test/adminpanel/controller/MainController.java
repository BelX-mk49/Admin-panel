package by.test.adminpanel.controller;

import by.test.adminpanel.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    private final UserService userService;

    public MainController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String main(Model model){
        model.addAttribute("users", userService.findAll());
        return "userList";
    }
}

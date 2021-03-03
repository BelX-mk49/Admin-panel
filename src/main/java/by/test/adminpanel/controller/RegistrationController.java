package by.test.adminpanel.controller;

import by.test.adminpanel.entity.User;
import by.test.adminpanel.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.Map;

@Controller
public class RegistrationController {

    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("registration")
    public String registration(Model model) {
        model.addAttribute("message", "");
        return ("/registration");
    }

    @PostMapping("registration")
    public String addUser(
            @RequestParam("password2") String passwordConfirm,
            @Valid User user,
            BindingResult bindingResult,
            Model model
    ) {
        if (userService.isPasswordDifferent(passwordConfirm, user)){
            bindingResult.addError(new FieldError("user", "password", "Password are different"));
            model.addAttribute("passwordError", "Password are different");
            return "registration";
        }
        if (userService.isConfirmEmpty(passwordConfirm) || bindingResult.hasErrors()) {
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
            model.addAttribute("password2Error", "Password confirmation can't be empty");
            model.mergeAttributes(errors);
            return "registration";
        }
        if (!userService.addUser(user)) {
            model.addAttribute("usernameError", "User exist!");
            return "registration";
        }
        return "redirect:/login";
    }
}

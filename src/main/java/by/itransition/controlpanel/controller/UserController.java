package by.itransition.controlpanel.controller;

import by.itransition.controlpanel.entity.User;
import by.itransition.controlpanel.dto.IdDto;
import by.itransition.controlpanel.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;

@Controller
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String userList(Model model){
        model.addAttribute("users", userService.findAll());
        return "userList";
    }

    @GetMapping("{user}")
    public String userEditForm(@PathVariable User user, Model model){
        model.addAttribute("user", user);
        return "userEdit";
    }

    @PostMapping
    public String userSave(@RequestParam String username, @RequestParam Map<String, String> form, @RequestParam("userId") User user) {
        userService.saveUser(user, username, form);
        return "redirect:/user";
    }

    @PostMapping("delete")
    public String userDelete(IdDto idDto) {
        userService.deleteUser(idDto.getUserId());
        return "redirect:/user";
    }
}

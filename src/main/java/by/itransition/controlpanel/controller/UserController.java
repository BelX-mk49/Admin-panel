package by.itransition.controlpanel.controller;

import by.itransition.controlpanel.entity.User;
import by.itransition.controlpanel.dto.IdDto;
import by.itransition.controlpanel.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String userList(Model model) {
        model.addAttribute("users", userService.findAll());
        return "userList";
    }

    @PostMapping(value = "/edit", params = "action=block")
    public String block(@RequestParam("userId") String[] userId) {
        userService.block(userId);
        return "redirect:/login?logout";
    }

    @PostMapping(value = "/edit", params = "action=delete")
    public String delete(@RequestParam("userId") String[] userId) {
        userService.delete(userId);
        return "redirect:/login?logout";
    }

    @PostMapping(value = "/edit", params = "action=unblock")
    public String unblock(@RequestParam("userId") String[] userId) {
        userService.unblock(userId);
        return "redirect:/login?logout";
    }
}

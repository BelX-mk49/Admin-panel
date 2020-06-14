package by.itransition.controlpanel.controller;

import by.itransition.controlpanel.entity.User;
import by.itransition.controlpanel.dto.IdDto;
import by.itransition.controlpanel.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

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

    @PostMapping("block")
    public String banUser(IdDto idDto) {
        userService.block(idDto.getUserId());
        return "redirect:/login?logout";
    }

    @PostMapping("delete")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String delete(IdDto idDto) {
        userService.delete(idDto.getUserId());
        return "redirect:/login?logout";
    }

    @PostMapping("unblock")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String unblock(IdDto idDto) {
        userService.unblock(idDto.getUserId());
        return "redirect:/login?logout";
    }
}

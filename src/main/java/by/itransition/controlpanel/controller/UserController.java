package by.itransition.controlpanel.controller;

import by.itransition.controlpanel.entity.User;
import by.itransition.controlpanel.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @PostMapping(value = "/edit")
    public String action(@RequestParam("userId") String[] userId,
                         @RequestParam(value = "action") String action,
                         @AuthenticationPrincipal User currentUser) {
        String redirect = "";
        if (action.equals("block")){
            redirect = userService.block(userId, currentUser);
        }else {
            redirect = userService.unblock(userId, currentUser);
        }
        return redirect;
    }

    @PostMapping(value = "/edit", params = "action=delete")
    public String delete(@RequestParam("userId") String[] userId, @AuthenticationPrincipal User currentUser) {
        String redirect = "";
        redirect = userService.delete(userId, currentUser);
        return redirect;
    }
}

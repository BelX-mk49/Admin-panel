package by.itransition.controlpanel.controller;

import by.itransition.controlpanel.entity.User;
import by.itransition.controlpanel.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
                         @AuthenticationPrincipal User currentUser,
                         HttpServletRequest request, HttpServletResponse response) {
        String redirect = "redirect:/";
        if (action.equals("block")){
            redirect = userService.block(userId, currentUser, redirect);
            if (redirect.equals("redirect:/login?logout")) {
                logoutPage(request, response);
            }
        }else
            redirect = userService.unblock(userId, currentUser, redirect);
        return redirect;
    }

    @PostMapping(value = "/edit", params = "action=delete")
    public String delete(@RequestParam("userId") String[] userId, @AuthenticationPrincipal User currentUser,
                         HttpServletRequest request, HttpServletResponse response) {
        String redirect = "redirect:/";
        redirect = userService.delete(userId, currentUser, redirect);
        if (redirect.equals("redirect:/login?logout")) {
            logoutPage(request, response);
        }
        return redirect;
    }

    @GetMapping(value = "/logout")
    public void logoutPage(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
    }
}

package com.bank.controller;

import com.bank.model.User;
import com.bank.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String showLoginPage() {
        return "login";
    }

    @PostMapping
    public String login() {
        return "redirect:/home";
    }

    @GetMapping("/forgot-password")
    public String showForgotPasswordForm() {
        return "auth/forgot-password";
    }

    @PostMapping("/forgot-password")
    public String handleForgotPassword(@RequestParam String username,
                                       @RequestParam String newPassword,
                                       Model model) {
        try {
            User user = userService.findUserByUsername(username);
            userService.updatePassword(user, newPassword);

            model.addAttribute("message", "Password updated successfully.");
        } catch (RuntimeException e) {
            model.addAttribute("error", "User not found.");
        }
        return "auth/forgot-password";
    }
}

package com.bank.controller;

import com.bank.model.User;
import com.bank.service.UserService;
import com.bank.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private AccountService accountService;

    @GetMapping("/users")
    public String listUsers(@RequestParam(value = "keyword", required = false) String keyword, Model model) {
        List<User> users;

        if (keyword != null && !keyword.isEmpty()) {
            users = userService.findByUsernameContaining(keyword);
        } else {
            users = userService.getAllUsers();
        }

        Map<Long, Double> userBalances = new HashMap<>();
        for (User user : users) {
            double balance = accountService.calculateTotalBalanceByUsername(user.getUsername());
            userBalances.put(user.getId(), balance);
        }

        model.addAttribute("users", users);
        model.addAttribute("userBalances", userBalances);
        model.addAttribute("keyword", keyword); // для отображения в форме поиска

        return "admin/users";
    }

    @PostMapping("/block-user/{id}")
    public String blockUser(@PathVariable Long id) {
        userService.blockUserById(id);
        return "redirect:/admin/users";
    }

    @PostMapping("/unblock-user/{id}")
    public String unblockUser(@PathVariable Long id) {
        userService.unblockUserById(id);
        return "redirect:/admin/users";
    }

    @PostMapping("/delete-user/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);
        return "redirect:/admin/users";
    }
}

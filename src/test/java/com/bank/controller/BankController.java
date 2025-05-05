package com.bank.controller;

import com.bank.model.Account;
import com.bank.model.User;
import com.bank.model.Customer;
import com.bank.service.AccountService;
import com.bank.service.BankService;
import com.bank.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
public class BankController {

    @Autowired
    private BankService bankService;

    @Autowired
    private UserService userService;

    @Autowired
    private AccountService accountService;

    @GetMapping("/home")
    public String homePage(Model model, Principal principal) {
        String username = principal.getName();
        User user = userService.findUserByUsername(username);


        if ("ROLE_ADMIN".equals(user.getRole())) {
            return "redirect:/admin/users";
        }

        List<Account> accounts = accountService.findAccountsByUsername(username);
        model.addAttribute("accounts", accounts);
        model.addAttribute("username", username);
        return "home";
    }


    @GetMapping("/customers")
    public String listCustomers(Model model) {
        model.addAttribute("customers", bankService.getAllCustomers());
        return "customers";
    }

    @PostMapping("/create-customer")
    public String createCustomer(@ModelAttribute Customer customer) {
        bankService.createCustomer(customer);
        return "redirect:/customers";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String username,
                               @RequestParam String password) {
        userService.registerUser(username, password);
        return "redirect:/login";
    }

    @GetMapping("/logout")
    public String logout() {
        return "redirect:/login?logout";
    }
}

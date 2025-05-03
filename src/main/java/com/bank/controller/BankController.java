package com.bank.controller;

import com.bank.model.Account;
import com.bank.model.User;
import com.bank.service.AccountService;
import com.bank.service.BankService;
import com.bank.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    @GetMapping("/deposit")
    public String depositPage() {
        return "operations/deposit";
    }

    @GetMapping("/withdraw")
    public String withdrawPage() {
        return "operations/withdraw";
    }

    @GetMapping("/transfer")
    public String transferPage() {
        return "operations/transfer";
    }

    @PostMapping("/deposit")
    public String deposit(@RequestParam String accountNumber,
                          @RequestParam double amount,
                          RedirectAttributes redirectAttributes) {
        try {
            bankService.depositByAccountNumber(accountNumber, amount);
            redirectAttributes.addFlashAttribute("success", "Deposit successful!");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/deposit";
    }

    @PostMapping("/withdraw")
    public String withdraw(@RequestParam String accountNumber,
                           @RequestParam double amount,
                           RedirectAttributes redirectAttributes) {
        try {
            bankService.withdrawByAccountNumber(accountNumber, amount);
            redirectAttributes.addFlashAttribute("success", "Withdrawal successful!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/withdraw";
    }

    @PostMapping("/transfer")
    public String transfer(@RequestParam String fromAccountNumber,
                           @RequestParam String toAccountNumber,
                           @RequestParam double amount,
                           @RequestParam(required = false) String comment,
                           RedirectAttributes redirectAttributes) {
        try {
            bankService.transfer(fromAccountNumber, toAccountNumber, amount, comment);
            redirectAttributes.addFlashAttribute("success", "Transfer completed!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/transfer";
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

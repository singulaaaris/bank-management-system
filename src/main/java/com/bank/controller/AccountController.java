package com.bank.controller;

import com.bank.model.Account;
import java.security.Principal;
import com.bank.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;

@Controller
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/accounts")
    public String viewAccounts(Model model, Principal principal) {
        String username = principal.getName();
        List<Account> accounts = accountService.findAccountsByUsername(username);

        model.addAttribute("accounts", accounts);
        model.addAttribute("username", username);
        model.addAttribute("email", username + "@bank.com");
        return "accounts";
    }
    @GetMapping("/account/{id}")
    public String getAccount(@PathVariable Long id, Model model) {
        try {
            Account account = accountService.getAccountById(id);
            model.addAttribute("account", account);
            return "account";
        } catch (Exception e) {
            model.addAttribute("error", "Account with this ID not found.");
            return "error-page";
        }
    }
}
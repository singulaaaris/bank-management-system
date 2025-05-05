package com.bank.controller;

import com.bank.model.Account;
import java.security.Principal;
import com.bank.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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


}
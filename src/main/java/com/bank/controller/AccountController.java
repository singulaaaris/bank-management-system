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

    // Обработчик запроса на отображение информации об аккаунте
    @GetMapping("/account/{id}")
    public String getAccount(@PathVariable Long id, Model model) {
        try {
            // Попытка получить аккаунт по ID
            Account account = accountService.getAccountById(id);
            model.addAttribute("account", account);
            return "account"; // Возвращаем страницу с деталями аккаунта
        } catch (Exception e) {
            // Если произошла ошибка (например, аккаунт не найден), добавляем ошибку в модель
            model.addAttribute("error", "Account with this ID not found.");
            return "error-page"; // Переходим на страницу с ошибкой
        }
    }
}
package com.bank.controller;

import com.bank.model.Transaction;
import com.bank.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/transactions")
    public String listUserTransactions(Model model, Principal principal) {
        String username = principal.getName(); // получить имя авторизованного пользователя
        List<Transaction> transactions = transactionService.getTransactionsByUsername(username);
        model.addAttribute("transactions", transactions);
        return "transactions";
    }


}

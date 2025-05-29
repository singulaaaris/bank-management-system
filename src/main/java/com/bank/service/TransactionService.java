package com.bank.service;

import com.bank.model.Account;
import com.bank.model.Transaction;
import com.bank.model.User;
import com.bank.repository.AccountRepository;
import com.bank.repository.TransactionRepository;
import com.bank.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountRepository accountRepository;


    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }


    public List<Transaction> getTransactionsByDateRange(Date startDate, Date endDate) {
        return transactionRepository.findByTimestampBetween(startDate, endDate);
    }


    public List<Transaction> getTransactionsByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Long customerId = user.getCustomer().getId();
        List<Account> accounts = accountRepository.findByCustomerId(customerId);

        List<Transaction> allTransactions = new ArrayList<>();
        for (Account account : accounts) {
            allTransactions.addAll(
                    transactionRepository.findByAccountIdOrderByTimestampDesc(account.getId())
            );
        }

        return allTransactions;
    }
    public List<Transaction> getTransactionsByUsernameAndDateRange(String username, LocalDate startDate, LocalDate endDate) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Long customerId = user.getCustomer().getId();
        List<Account> accounts = accountRepository.findByCustomerId(customerId);

        List<Transaction> all = new ArrayList<>();
        for (Account account : accounts) {
            all.addAll(transactionRepository.findByAccountIdAndTimestampBetween(
                    account.getId(),
                    java.sql.Date.valueOf(startDate),
                    java.sql.Date.valueOf(endDate.plusDays(1))
            ));
        }
        return all;
    }

}

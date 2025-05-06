package com.bank.service;


import com.bank.model.Account;
import com.bank.model.Transaction;
import com.bank.model.User;
import com.bank.repository.AccountRepository;
import com.bank.repository.TransactionRepository;
import com.bank.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountRepository accountRepository;

    public List<Transaction> getTransactionsByDateRange(Date startDate, Date endDate) {
        return transactionRepository.findByTimestampBetween(startDate, endDate);
    }



    public List<Transaction> getTransactionsByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Long customerId = user.getCustomer().getId();

        List<Account> accounts = accountRepository.findByCustomerId(customerId);

        List<Transaction> all = new ArrayList<>();
        for (Account acc : accounts) {
            all.addAll(transactionRepository.findByAccountIdOrderByTimestampDesc(acc.getId()));
        }

        return all;
    }

}
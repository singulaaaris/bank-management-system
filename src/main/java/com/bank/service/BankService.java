package com.bank.service;

import com.bank.model.Account;
import com.bank.model.Customer;
import com.bank.model.Transaction;
import com.bank.repository.AccountRepository;
import com.bank.repository.CustomerRepository;
import com.bank.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BankService {
    @Autowired
    private CustomerRepository customerRepo;

    @Autowired
    private AccountRepository accountRepo;

    public List<Customer> getAllCustomers() {
        return customerRepo.findAll();
    }

    public void createCustomer(Customer customer) {
        customerRepo.save(customer);
    }

//    public void deposit(Long accountId, double amount) {
//        Account account = accountRepo.findById(accountId).orElseThrow();
//        account.setBalance(account.getBalance() + amount);
//        accountRepo.save(account);
//    }
//
//    public void withdraw(Long accountId, double amount) {
//        Account account = accountRepo.findById(accountId).orElseThrow();
//        if (account.getBalance() >= amount) {
//            account.setBalance(account.getBalance() - amount);
//            accountRepo.save(account);
//        } else {
//            throw new RuntimeException("Insufficient funds!");
//        }
//    }

    @Autowired
    private TransactionRepository transactionRepository;

    public void deposit(Long accountId, double amount) {
        Account account = accountRepo.findById(accountId).orElseThrow(() -> new RuntimeException("Account not found!"));
        account.setBalance(account.getBalance() + amount);
        accountRepo.save(account);

        // Log transaction
        Transaction transaction = new Transaction();
        transaction.setTransactionType("Deposit");
        transaction.setAmount(amount);
        transaction.setAccount(account);
        transactionRepository.save(transaction);
    }

    public void withdraw(Long accountId, double amount) {
        Account account = accountRepo.findById(accountId).orElseThrow(() -> new RuntimeException("Account not found!"));
        if (account.getBalance() >= amount) {
            account.setBalance(account.getBalance() - amount);
            accountRepo.save(account);

            // Log transaction
            Transaction transaction = new Transaction();
            transaction.setTransactionType("Withdrawal");
            transaction.setAmount(amount);
            transaction.setAccount(account);
            transactionRepository.save(transaction);
        } else {
            throw new RuntimeException("Insufficient funds!");
        }
    }

}
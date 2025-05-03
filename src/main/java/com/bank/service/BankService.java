package com.bank.service;

import com.bank.model.Account;
import com.bank.model.Customer;
import com.bank.model.Transaction;
import com.bank.repository.AccountRepository;
import com.bank.repository.CustomerRepository;
import com.bank.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class BankService {

    @Autowired
    private CustomerRepository customerRepo;

    @Autowired
    private AccountRepository accountRepo;

    @Autowired
    private TransactionRepository transactionRepository;

    public List<Customer> getAllCustomers() {
        return customerRepo.findAll();
    }

    public void createCustomer(Customer customer) {
        customerRepo.save(customer);
    }

    @Transactional
    public void depositByAccountNumber(String accountNumber, double amount) {
        if (amount < 100) {
            throw new IllegalArgumentException("Minimum deposit amount is 100 KZT.");
        }
        validateAmount(amount);
        Account account = getAccountByNumber(accountNumber);
        account.setBalance(account.getBalance() + amount);
        accountRepo.save(account);
        logTransaction(account, amount, "Deposit", null);
    }

    @Transactional
    public void withdrawByAccountNumber(String accountNumber, double amount) {
        validateAmount(amount);
        Account account = getAccountByNumber(accountNumber);
        if (account.getBalance() < amount) {
            throw new RuntimeException("Insufficient funds!");
        }
        account.setBalance(account.getBalance() - amount);
        accountRepo.save(account);
        logTransaction(account, amount, "Withdrawal", null);
    }

    @Transactional
    public void transfer(String fromAccountNumber, String toAccountNumber, double amount, String comment) {
        validateAmount(amount);

        if (fromAccountNumber.equals(toAccountNumber)) {
            throw new IllegalArgumentException("Cannot transfer to the same account.");
        }

        Account from = getAccountByNumber(fromAccountNumber);
        Account to = getAccountByNumber(toAccountNumber);

        if (from.getBalance() < amount) {
            throw new RuntimeException("Insufficient funds for transfer!");
        }

        from.setBalance(from.getBalance() - amount);
        to.setBalance(to.getBalance() + amount);

        accountRepo.save(from);
        accountRepo.save(to);

        logTransaction(from, amount, "Transfer Out", comment);
        logTransaction(to, amount, "Transfer In", comment);
    }

    private Account getAccountByNumber(String accountNumber) {
        return accountRepo.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new RuntimeException("Account not found with number: " + accountNumber));
    }

    private void logTransaction(Account account, double amount, String type, String comment) {
        Transaction tx = new Transaction();
        tx.setTransactionType(type);
        tx.setAmount(amount);
        tx.setTimestamp(new Date());
        tx.setAccount(account);
        tx.setComment(comment);
        transactionRepository.save(tx);
    }

    private void validateAmount(double amount) {
        if (amount < 100) {
            throw new IllegalArgumentException("Minimum amount is 100 KZT");
        }
    }


    public List<Transaction> getTransactionsByAccountNumber(String accountNumber) {
        Account account = getAccountByNumber(accountNumber);
        return transactionRepository.findByAccountIdOrderByTimestampDesc(account.getId());

    }
}

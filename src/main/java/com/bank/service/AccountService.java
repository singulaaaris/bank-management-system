package com.bank.service;

import com.bank.model.Account;
import com.bank.model.Customer;
import com.bank.model.User;
import com.bank.repository.AccountRepository;
import com.bank.repository.CustomerRepository;
import com.bank.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomerRepository customerRepository;


    public List<Account> findAccountsByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getCustomer() == null) {
            throw new RuntimeException("User has no customer");
        }

        return accountRepository.findByCustomerId(user.getCustomer().getId());
    }

    public double calculateTotalBalanceByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));

        if (user.getCustomer() == null) {
            return 0.0;
        }

        List<Account> accounts = accountRepository.findByCustomerId(user.getCustomer().getId());

        return accounts.stream()
                .mapToDouble(Account::getBalance)
                .sum();
    }


    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public Account createAccount(Customer customer, String accountType, double initialBalance) {
        Account account = new Account();
        account.setCustomer(customer);
        account.setAccountType(accountType);
        account.setBalance(initialBalance);
        account.setAccountNumber(generateAccountNumber());
        return accountRepository.save(account);
    }

    public String generateAccountNumber() {
        String prefix = "4400";
        Random random = new Random();
        StringBuilder sb = new StringBuilder(prefix);

        for (int i = 0; i < 8; i++) {
            sb.append(random.nextInt(10));
        }

        return sb.toString();
    }

    public Account getAccountById(Long id) {
        Optional<Account> account = accountRepository.findById(id);
        if (account.isPresent()) {
            return account.get();
        } else {
            throw new RuntimeException("Account not found");
        }
    }
}

package com.bank.service;

import com.bank.model.User;
import com.bank.model.Account;
import com.bank.model.Customer;
import com.bank.repository.UserRepository;
import com.bank.repository.AccountRepository;
import com.bank.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public User registerUser(String username, String password) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new RuntimeException("A user with this username already exists.");
        }


        Customer customer = new Customer();
        customer.setName(username);
        customer.setEmail(username + "@bank.com");
        customerRepository.save(customer);


        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole("ROLE_USER");
        user.setEnabled(true);
        user.setCustomer(customer);
        userRepository.save(user);


        Account account = new Account();
        account.setCustomer(customer);
        account.setAccountType("Основной счёт");
        account.setBalance(0.0);
        account.setAccountNumber(generateAccountNumber());
        accountRepository.save(account);

        return user;
    }

    public String generateAccountNumber() {
        String prefix = "4400";
        StringBuilder sb = new StringBuilder(prefix);
        for (int i = 0; i < 8; i++) {
            sb.append((int) (Math.random() * 10));
        }
        return sb.toString();
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }


    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));
    }


    public void blockUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        user.setEnabled(false);
        userRepository.save(user);
    }


    public void unblockUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        user.setEnabled(true);
        userRepository.save(user);
    }


    @Transactional
    public void deleteUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));


        accountRepository.deleteAllByCustomerId(user.getCustomer().getId());
        userRepository.deleteById(id);
    }

    public void updatePassword(User user, String newPassword) {
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    public void deleteAllUsers() {
        userRepository.deleteAll();
    }
}

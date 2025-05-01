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

    // Регистрация нового пользователя
    public User registerUser(String username, String password) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new RuntimeException("A user with this username already exists.");
        }

        // Создаем нового клиента
        Customer customer = new Customer();
        customer.setName(username);
        customer.setEmail(username + "@bank.com");
        customerRepository.save(customer);

        // Создаем нового пользователя
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password)); // Хешируем пароль
        user.setRole("ROLE_USER");  // Роль пользователя по умолчанию
        user.setEnabled(true);
        user.setCustomer(customer);
        userRepository.save(user);

        // Создаем основной аккаунт для пользователя
        Account account = new Account();
        account.setCustomer(customer);
        account.setAccountType("Основной счёт");
        account.setBalance(0.0);  // Изначально нулевой баланс
        account.setAccountNumber(generateAccountNumber());
        accountRepository.save(account);

        return user;
    }

    // Генерация случайного номера аккаунта
    public String generateAccountNumber() {
        String prefix = "4400";
        StringBuilder sb = new StringBuilder(prefix);
        for (int i = 0; i < 8; i++) {
            sb.append((int) (Math.random() * 10)); // Генерация случайных цифр
        }
        return sb.toString();
    }

    // Получение всех пользователей
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Получение пользователя по username
    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));
    }

    // Блокировка пользователя
    public void blockUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        user.setEnabled(false);
        userRepository.save(user); // Сохраняем изменения
    }

    // Разблокировка пользователя
    public void unblockUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        user.setEnabled(true);
        userRepository.save(user); // Сохраняем изменения
    }

    // Удаление пользователя по ID
    @Transactional
    public void deleteUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        // Удаляем все аккаунты, связанные с пользователем
        accountRepository.deleteAllByCustomerId(user.getCustomer().getId());

        // Удаляем пользователя
        userRepository.deleteById(id);
    }

    // Удаление всех пользователей
    public void deleteAllUsers() {
        userRepository.deleteAll();
    }
}

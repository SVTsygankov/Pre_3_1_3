package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.User;
import java.util.List;
import java.util.Optional;

public interface UserService {
    public List<User> findAll();
    public User findUserById(Long id);
    public Optional findUserByLogin(String login);
    public boolean deleteUser(Long id);
    public boolean saveUser(User user);
    public void updateUser(User user, Long id);
}

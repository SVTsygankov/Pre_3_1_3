package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.User;
import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> findAll();

    User findUserById(Long id);

    Optional findUserByLogin(String login);

    boolean deleteUser(Long id);

    boolean saveUser(User user);

    void updateUser(User user, Long id);
}

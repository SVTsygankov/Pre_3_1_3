package ru.kata.spring.boot_security.demo.service;

import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.UsersRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
public class UserServiceImp implements UserService, UserDetailsService {

    private final UsersRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    public UserServiceImp(UsersRepository userRepository, @Lazy PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<User> findAll() { return userRepository.findAll(); }

     @Override
    public User findUserById(Long id) {
        if (userRepository.findById(id).isEmpty())
            throw new UsernameNotFoundException("Пользователь с ID="+id+" не найден");
        return userRepository.findById(id).get();
    }

    @Override
    @Transactional
    public boolean deleteUser(Long id) {
        if(userRepository.findById(id).isPresent()) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public boolean saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return true;
    }

    @Override
    public Optional<User> findUserByLogin(String login) {
//        if (userRepository.findByLogin(login).isEmpty())
//            throw new UsernameNotFoundException("Пользователя с login = " + login + "не существует");
        return userRepository.findByLogin(login);
//                .get();
    }

   public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByLogin(login);
        if (user.isEmpty()) throw new UsernameNotFoundException("Пользователя с login = " + login + " не найден");
        return  user.get();
        }

    @Transactional
    @Override
    public void updateUser(User updateUser, Long id) {
        updateUser.setId(id);
        if (!updateUser.getPassword().equals(userRepository.findById(id).get().getPassword())) {
            updateUser.setPassword(passwordEncoder.encode(updateUser.getPassword()));
        }
        userRepository.save(updateUser);
    }
}

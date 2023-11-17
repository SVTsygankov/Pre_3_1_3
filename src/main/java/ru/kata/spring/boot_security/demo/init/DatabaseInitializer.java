package ru.kata.spring.boot_security.demo.init;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RolesRepository;
import ru.kata.spring.boot_security.demo.repository.UsersRepository;
import java.util.ArrayList;
import java.util.List;

@Component
public class DatabaseInitializer implements ApplicationListener<ContextRefreshedEvent> {

    private final RolesRepository roleRepository;
    private final UsersRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DatabaseInitializer(RolesRepository roleRepository,
                               UsersRepository userRepository,
                               PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        Role role1 = new Role();
        role1.setRole("ROLE_USER");
        Role role2 = new Role();
        role2.setRole("ROLE_ADMIN");
        Role role3 = new Role();
        role3.setRole("ROLE_BUYER");

        roleRepository.save(role1);
        roleRepository.save(role2);
        roleRepository.save(role3);

        List<Role> adminRoles = new ArrayList<>();
        adminRoles.add(role1);
        adminRoles.add(role2);
        List<Role> userRoles = new ArrayList<>();
        userRoles.add(role1);

        User admin = new User();
        admin.setLogin("admin");
        admin.setPassword(passwordEncoder.encode("admin"));
        admin.setRoles(adminRoles);
        admin.setFirstName("Сергей");
        admin.setLastName("Цыганков");
        admin.setEmail("s.v.tsygankov@mail.ru");
        userRepository.save(admin);

        User user = new User();
        user.setLogin("user");
        user.setPassword(passwordEncoder.encode("user"));
        user.setRoles(userRoles);
        user.setFirstName("Сидор");
        user.setLastName("Сидоров");
        user.setEmail("Sidorov@gmail.com");
        userRepository.save(user);
    }
}


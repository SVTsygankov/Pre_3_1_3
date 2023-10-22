package ru.kata.spring.boot_security.demo.service;

import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.repository.RolesRepository;
import java.util.List;


@Service
public class RoleServiceImp implements RoleService {

    private final RolesRepository rolesRepository;

    public RoleServiceImp(RolesRepository roleRepository) {
        this.rolesRepository = roleRepository;
    }
    @Override
    public List<Role> findAll() {
        return rolesRepository.findAll();
    }
}

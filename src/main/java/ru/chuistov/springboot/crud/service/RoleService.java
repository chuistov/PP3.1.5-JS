package ru.chuistov.springboot.crud.service;

import org.springframework.stereotype.Service;
import ru.chuistov.springboot.crud.dto.RoleDto;
import ru.chuistov.springboot.crud.entities.Role;
import ru.chuistov.springboot.crud.repositories.RoleRepository;

import java.util.List;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    public Role findRoleByRoleName(String roleName) {
        return roleRepository.findRoleByRoleName(roleName);
    }

    public List<RoleDto> findAllRoleDtos() {
        return findAll().stream()
                .map(RoleDto::new)
                .toList();
    }
}

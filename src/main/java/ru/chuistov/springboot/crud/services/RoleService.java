package ru.chuistov.springboot.crud.services;

import org.springframework.stereotype.Service;
import ru.chuistov.springboot.crud.dto.UserDto;
import ru.chuistov.springboot.crud.entities.Role;
import ru.chuistov.springboot.crud.entities.User;
import ru.chuistov.springboot.crud.repositories.RoleRepository;

import java.util.List;
import java.util.Set;

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

    public List<Role> getRolesFromDto(UserDto userDto) {
        return userDto.getRoles().stream()
                .map(this::findRoleByRoleName)
                .toList();
    }
}

package ru.chuistov.springboot.crud.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.chuistov.springboot.crud.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findRoleByRoleName(String roleName);
}

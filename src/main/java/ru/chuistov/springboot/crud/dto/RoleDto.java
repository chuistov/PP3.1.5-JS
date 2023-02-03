package ru.chuistov.springboot.crud.dto;

import ru.chuistov.springboot.crud.entities.Role;

public class RoleDto {
    private final String roleName;

    public RoleDto(Role role) {
        this.roleName = role.toString();
    }
}

package ru.chuistov.springboot.crud.dto;

import lombok.Data;
import ru.chuistov.springboot.crud.entities.Role;

@Data
public class RoleDto {
    private final int id;
    private final String roleName;

    public RoleDto(Role role) {
        this.id = role.getId();
        this.roleName = role.toString();
    }
}

package ru.chuistov.springboot.crud.dto;

import lombok.Data;
import ru.chuistov.springboot.crud.entities.Role;

import java.io.Serializable;

@Data
public class RoleDto {
    private final String roleName;

    public RoleDto(Role role) {
        this.roleName = role.toString();
    }
}

package ru.chuistov.springboot.crud.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.chuistov.springboot.crud.dto.RoleDto;
import ru.chuistov.springboot.crud.services.RoleService;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RoleRestController {

    private final RoleService roleService;

    @Autowired
    public RoleRestController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/")
    public List<RoleDto> getAllRoles() {
        System.out.println("getting roles");
        return roleService.findAll().stream()
                .map(RoleDto::new)
                .toList();
    }
}

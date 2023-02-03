package ru.chuistov.springboot.crud.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.chuistov.springboot.crud.dto.RoleDto;
import ru.chuistov.springboot.crud.dto.UserDto;
import ru.chuistov.springboot.crud.entities.Role;
import ru.chuistov.springboot.crud.entities.User;
import ru.chuistov.springboot.crud.security.UserDetailsImpl;
import ru.chuistov.springboot.crud.services.RoleService;
import ru.chuistov.springboot.crud.services.UserService;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserRestController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public UserRestController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/all")
    public List<UserDto> getAllUsers() {
        return userService.findAll().stream()
                .map(UserDto::new)
                .toList();
    }

    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable("id") long id) {
        User user = userService.findById(id);
        return new UserDto(user);
    }

    @GetMapping("/auth")
    public UserDto getAuthorizedUser() {
        User user = ((UserDetailsImpl) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal())
                .getUser();
        return new UserDto(user);
    }

    @PatchMapping("/edit/{id}")
    public ResponseEntity<HttpStatus> editUser(@PathVariable("id") long id, @RequestBody UserDto userDto) {
        List<Role> roles = roleService.getRolesFromDto(userDto);
//        String password = userService.getUserPassword(userDto);
        User user = new User(userDto, roles);
        userService.update(user);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<HttpStatus> addUser(@RequestBody UserDto userDto) {
        List<Role> roles = roleService.getRolesFromDto(userDto);
        User user = new User(userDto, roles);
        userService.save(user);

        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") long id) {
        System.out.println("deleting user with id " + id);
        userService.deleteById(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}

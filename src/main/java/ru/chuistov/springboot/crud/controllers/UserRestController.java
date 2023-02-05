package ru.chuistov.springboot.crud.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.chuistov.springboot.crud.dto.UserDto;
import ru.chuistov.springboot.crud.entities.User;
import ru.chuistov.springboot.crud.services.UserService;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserRestController {

    private final UserService userService;

    @Autowired
    public UserRestController(UserService userService) {
        this.userService = userService;
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
        return userService.getAuthorizedUser();
    }

    @PatchMapping("/edit/{id}")
    public ResponseEntity<HttpStatus> editUser(@PathVariable("id") long id, @RequestBody UserDto userDto) {
        userService.update(userDto);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<HttpStatus> addUser(@RequestBody UserDto userDto) {
        userService.save(userDto);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") long id) {
        userService.deleteById(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}

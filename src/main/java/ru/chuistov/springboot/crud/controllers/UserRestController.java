package ru.chuistov.springboot.crud.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.chuistov.springboot.crud.dto.UserDto;
import ru.chuistov.springboot.crud.entities.User;
import ru.chuistov.springboot.crud.security.UserDetailsImpl;
import ru.chuistov.springboot.crud.services.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        List<User> users = userService.findAll();
        List<UserDto> userDtoList = new ArrayList<>();
        for (User user : users) {
            userDtoList.add(new UserDto(user));
        }
        return userDtoList;
    }

    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable("id") long id) {
        User user = userService.findById(id);
        return new UserDto(user);
    }

    @GetMapping("/auth")
    public UserDto getAuthUser() {
        return new UserDto(getAuthorizedUser());
    }

    private User getAuthorizedUser() {
        var context = SecurityContextHolder.getContext();
        var objectPrincipal = context
                .getAuthentication()
                .getPrincipal();
        var principal = (UserDetailsImpl) objectPrincipal;
        return principal.getUser();
    }

}

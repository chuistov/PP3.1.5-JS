package ru.chuistov.springboot.crud.dto;

import lombok.Data;
import ru.chuistov.springboot.crud.entities.Role;
import ru.chuistov.springboot.crud.entities.User;
import java.util.stream.Collectors;

@Data
public class UserDto {

    private long id;
    private final String name;
    private final String lastName;
    private final int age;
    private final String email;
    private final String password;
    private final String rolesString;

    public UserDto(long id, String name, String lastName, int age, String email, String password, String rolesString) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.age = age;
        this.email = email;
        this.password = password;
        this.rolesString = rolesString;
    }

    public UserDto(User user) {
        id = user.getId();
        name = user.getName();
        lastName = user.getLastName();
        age = user.getAge();
        email = user.getEmail();
        password = user.getPassword();
        rolesString = user.getRoles().stream()
                .map(Role::toString)
                .collect(Collectors.joining(", "));
    }
}

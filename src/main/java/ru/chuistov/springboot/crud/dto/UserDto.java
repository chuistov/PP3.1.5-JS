package ru.chuistov.springboot.crud.dto;

import lombok.Data;
import ru.chuistov.springboot.crud.entities.Role;
import ru.chuistov.springboot.crud.entities.User;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class UserDto {

    private long id;

    // TODO: move validation annotations to User class
    @NotEmpty(message = "First name should not be empty")
    @Size(min = 2, max = 30, message = "First name can contain 2 to 30 characters")
    private final String name;

    @NotEmpty(message = "Last name should not be empty")
    @Size(min = 2, max = 30, message = "Last name can contain 2 to 30 characters")
    private final String lastName;

    @Min(value = 0, message = "Age should be positive")
    private final int age;

    @Email
    private final String email;
    private final String password;
    private final String rolesString;

   /* public UserDto(String name, String lastName, int age, String email, String password, String rolesString) {
        this.name = name;
        this.lastName = lastName;
        this.age = age;
        this.email = email;
        this.password = password;
        this.rolesString = rolesString;
    }*/

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
                .map(role -> role.toString())
                .collect(Collectors.joining(", "));
    }
}

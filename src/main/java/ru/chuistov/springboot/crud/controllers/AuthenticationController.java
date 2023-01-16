package ru.chuistov.springboot.crud.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.chuistov.springboot.crud.entities.Role;
import ru.chuistov.springboot.crud.entities.User;
import ru.chuistov.springboot.crud.services.RegistrationService;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/authentication")
public class AuthenticationController {

    private final RegistrationService registrationService;
    private final List<Role> allRoles;

    @Autowired
    public AuthenticationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
        allRoles = registrationService.getAllAvailableRoles();
    }

    @GetMapping("/login")
    public String openLoginPage() {
        return "authentication/login";
    }

    @GetMapping("/register")
    public String openRegistrationPage(Model model) {

        // Creating a user with default role "user" and sending it
        // to the page just in case one wants to create new user
        User newUser = new User();
        newUser.getRoles().add(allRoles.get(1));

        model.addAttribute("user", newUser);
        model.addAttribute("roles", allRoles);
        return "authentication/register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user) {
        registrationService.register(user);
        return "redirect:/login";
    }
}

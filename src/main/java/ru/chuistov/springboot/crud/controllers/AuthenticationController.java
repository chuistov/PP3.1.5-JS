package ru.chuistov.springboot.crud.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.chuistov.springboot.crud.entities.User;
import ru.chuistov.springboot.crud.services.RegistrationService;

@Controller
@RequestMapping("/authentication")
public class AuthenticationController {

    private final RegistrationService registrationService;

    @Autowired
    public AuthenticationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "authentication/login";
    }

    @GetMapping("/register")
    public String registrationPage(@ModelAttribute("user") User user) {
        return "authentication/register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user) {
        registrationService.register(user);
        return "redirect:/login";
    }
}

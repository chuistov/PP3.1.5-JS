package ru.chuistov.springboot.crud.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
public class UserController {

    @GetMapping("/admin")
    public String showAdminPage() {
        return "adminRest";
    }

    @GetMapping("/user")
    public String showUserPage() {
        return "userRest";
    }
}
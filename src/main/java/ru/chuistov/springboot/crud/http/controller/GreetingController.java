package ru.chuistov.springboot.crud.http.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.chuistov.springboot.crud.security.UserDetailsImpl;

@Controller
@RequestMapping()
public class GreetingController {

    @GetMapping("/")
    public String greetingPage() {
        return "/greeting";
    }


}

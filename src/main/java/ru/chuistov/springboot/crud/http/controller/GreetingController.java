package ru.chuistov.springboot.crud.http.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping()
public class GreetingController {

    @GetMapping("/")
    public String greetingPage() {
        return "/greeting";
    }


}

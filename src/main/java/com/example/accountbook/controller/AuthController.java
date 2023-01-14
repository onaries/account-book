package com.example.accountbook.controller;


import com.example.accountbook.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class AuthController {

    private UserService userService;

    @GetMapping(value = "/login")
    public String getLogin(Model model) {

        model.addAttribute("page", "login");

        return "login.html";
    }
}


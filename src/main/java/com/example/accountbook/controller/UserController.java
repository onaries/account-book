package com.example.accountbook.controller;

import com.example.accountbook.model.User;
import com.example.accountbook.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping(value = "/")
    public List<User> getUser() {

        return this.userService.getUserList();
    }

    @GetMapping(value = "/{username}")
    public User getUserByUsername(@PathVariable String username) {
        return this.userService.getUserByUsername(username).orElse(null);
    }

}

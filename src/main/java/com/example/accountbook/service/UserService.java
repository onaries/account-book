package com.example.accountbook.service;


import com.example.accountbook.model.User;
import com.example.accountbook.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public List<User> getUserList() {
        return this.userRepository.findAll();
    }

    public Optional<User> getUserByUsername(String username) {
        return this.userRepository.findByUsername(username);
    }
 

    private UserDetails createUser(User user) {
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(passwordEncoder.encode(user.getPassword()))
                .build();
    }
}

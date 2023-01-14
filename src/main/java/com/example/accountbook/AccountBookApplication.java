package com.example.accountbook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class AccountBookApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccountBookApplication.class, args);
    }

}

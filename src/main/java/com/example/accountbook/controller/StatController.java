package com.example.accountbook.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
public class StatController {

    private final Logger logger = LoggerFactory.getLogger(StatController.class);

    @GetMapping("/api/stat")
    public ResponseEntity<?> getStat(@RequestParam String date) {

        LocalDate localDate = LocalDate.parse(date);
        logger.info(localDate.toString());

        return ResponseEntity.ok("stat");
    }

}


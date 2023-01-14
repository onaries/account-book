package com.example.accountbook.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@Slf4j
@RestController
public class StatController {

    @GetMapping("/api/stat")
    public ResponseEntity<?> getStat(@RequestParam String date) {

        LocalDate localDate = LocalDate.parse(date);
        log.info(localDate.toString());

        return ResponseEntity.ok("stat");
    }

}


package com.example.accountbook.controller;


import com.example.accountbook.config.NotificationManager;
import com.example.accountbook.model.AccountCard;
import com.example.accountbook.model.Category;
import com.example.accountbook.model.Statement;
import com.example.accountbook.service.NotionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
public class AdminController {

    private final NotificationManager notificationManager;
    private final NotionService notionService;

    @GetMapping(value = "/admin")
    public String getAdminPage() {

        return "admin";
    }


    @GetMapping(value = "/api/telegram")
    @ResponseBody
    public String sendTelegramMessage(@RequestParam String message) {

        notificationManager.sendNotification(message);

        return "send";
    }

    @GetMapping(value = "/api/notion")
    @ResponseBody
    public String sendNotionMessage(@RequestParam String message) {

        Statement statement = new Statement();
        statement.setAmount(1000);

        Category category = new Category();
        category.setName("주유비");
        category.setType(2);
        statement.setCategory(category);

        AccountCard accountCard = new AccountCard();
        accountCard.setName("신한더모아");
        statement.setAccountCard(accountCard);

        statement.setDate(LocalDateTime.now());
        statement.setName("기름");

        notionService.sendPostRequest(statement);

        return "send";
    }
}

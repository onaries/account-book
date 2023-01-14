package com.example.accountbook.config;

import com.example.accountbook.service.TelegramService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class NotificationManager {

    private final TelegramService telegramService;

    public NotificationManager(TelegramService telegramService) {
        this.telegramService = telegramService;
    }

    public void sendNotification(String message) {
        telegramService.sendTelegramMessage(message);
    }
}

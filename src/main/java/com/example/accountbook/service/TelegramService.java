package com.example.accountbook.service;

import com.example.accountbook.model.TelegramMessage;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class TelegramService {

    @Value("${telegram.bot.token}")
    private String botToken;

    @Value("${telegram.channel.chat_id}")
    private String channelChatId;

    public void sendTelegramMessage(String message) {

        String url = "https://api.telegram.org/bot" + botToken + "/sendMessage";

        try {
            TelegramMessage telegramMessage = new TelegramMessage(channelChatId, message);
            String params = new Gson().toJson(telegramMessage);

            log.info("params: " + params);

            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);

            HttpEntity<String> entity = new HttpEntity<>(params, headers);
            restTemplate.postForObject(url, entity, String.class);
        } catch (Exception e) {
            log.error("Error while sending telegram message", e);
        }
    }


}

package com.example.accountbook.service;


import com.example.accountbook.model.Statement;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.format.DateTimeFormatter;

@Slf4j
@Service
public class NotionService {

    private final Logger logger = LoggerFactory.getLogger(NotionService.class);

    @Value("${notion.token}")
    private String NOTION_TOKEN;

    @Value("${notion.database_id}")
    private String DATABASE_ID;

    private static final String CREATE_PAGE_URL = "https://api.notion.com/v1/pages";

    public void sendPostRequest(Statement statement) {
        logger.info("notion token: " + NOTION_TOKEN);
        logger.info("notion database id: " + DATABASE_ID);

        String categoryName = statement.getCategory().getName();
        String accountCardName = statement.getAccountCard().getName();
        int type = statement.getCategory().getType();
        String name = statement.getName();
        int amount = statement.getAmount();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String dateString = formatter.format(statement.getDate());

        JsonObject jsonObject = new JsonObject();
        JsonObject parent = new JsonObject();
        parent.addProperty("database_id", DATABASE_ID);
        jsonObject.add("parent", parent);

        JsonObject properties = new JsonObject();
        JsonObject amountJson = new JsonObject();
        amountJson.addProperty("number", amount);
        properties.add("금액", amountJson);

        JsonObject dateJson = new JsonObject();
        JsonObject dateStartJson = new JsonObject();
        dateStartJson.addProperty("start", dateString);
        dateJson.add("date", dateStartJson);
        properties.add("날짜", dateJson);

        JsonObject categoryJson = new JsonObject();
        JsonObject categoryNameJson = new JsonObject();
        categoryNameJson.addProperty("name", categoryName);
        categoryJson.add("select", categoryNameJson);
        properties.add("분류", categoryJson);

        JsonObject typeJson = new JsonObject();
        JsonObject typeNameJson = new JsonObject();

        String typeName = switch (type) {
            case 1 -> "수입";
            case 2 -> "지출";
            case 3 -> "이체";
            default -> "없음";
        };
        typeNameJson.addProperty("name", typeName);
        typeJson.add("select", typeNameJson);
        properties.add("타입", typeJson);

        JsonObject accountCardJson = new JsonObject();
        JsonObject accountCardNameJson = new JsonObject();
        accountCardNameJson.addProperty("name", accountCardName);
        accountCardJson.add("select", accountCardNameJson);
        properties.add("계좌/카드", accountCardJson);

        JsonObject nameJson = new JsonObject();
        JsonArray nameArray = new JsonArray();
        JsonObject nameTypeJson = new JsonObject();
        nameTypeJson.addProperty("type", "text");

        JsonObject nameTextJson = new JsonObject();
        JsonObject nameTextContentJson = new JsonObject();
        nameTextContentJson.addProperty("content", name);
        nameTextJson.add("text", nameTextContentJson);
        nameArray.add(nameTextJson);
        nameJson.add("title", nameArray);
        properties.add("이름", nameJson);
        jsonObject.add("properties", properties);

        String params = new Gson().toJson(jsonObject);
        logger.info(params);

        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + NOTION_TOKEN);
            headers.set("Notion-Version", "2022-06-28");
            headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);

            HttpEntity<String> entity = new HttpEntity<>(params, headers);
            restTemplate.postForObject(CREATE_PAGE_URL, entity, String.class);
        } catch (Exception e) {
            logger.error("Error while sending notion message", e);
        }
    }
}

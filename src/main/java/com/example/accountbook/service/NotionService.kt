package com.example.accountbook.service

import com.example.accountbook.model.Statement
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import java.time.format.DateTimeFormatter

@Service
class NotionService {
    private val logger = LoggerFactory.getLogger(NotionService::class.java)

    @Value("\${notion.token}")
    private val NOTION_TOKEN: String? = null

    @Value("\${notion.database_id}")
    private val DATABASE_ID: String? = null
    fun sendPostRequest(statement: Statement) {
        logger.info("notion token: $NOTION_TOKEN")
        logger.info("notion database id: $DATABASE_ID")
        val categoryName = statement.category.name
        val accountCardName = statement.accountCard.name
        val type = statement.category.type
        val name = statement.name
        val amount = statement.amount
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val dateString = formatter.format(statement.date)
        val jsonObject = JsonObject()
        val parent = JsonObject()
        parent.addProperty("database_id", DATABASE_ID)
        jsonObject.add("parent", parent)
        val properties = JsonObject()
        val amountJson = JsonObject()
        amountJson.addProperty("number", amount)
        properties.add("금액", amountJson)
        val dateJson = JsonObject()
        val dateStartJson = JsonObject()
        dateStartJson.addProperty("start", dateString)
        dateJson.add("date", dateStartJson)
        properties.add("날짜", dateJson)
        val categoryJson = JsonObject()
        val categoryNameJson = JsonObject()
        categoryNameJson.addProperty("name", categoryName)
        categoryJson.add("select", categoryNameJson)
        properties.add("분류", categoryJson)
        val typeJson = JsonObject()
        val typeNameJson = JsonObject()
        val typeName = when (type) {
            1 -> "수입"
            2 -> "지출"
            3 -> "이체"
            else -> "없음"
        }
        typeNameJson.addProperty("name", typeName)
        typeJson.add("select", typeNameJson)
        properties.add("타입", typeJson)
        val accountCardJson = JsonObject()
        val accountCardNameJson = JsonObject()
        accountCardNameJson.addProperty("name", accountCardName)
        accountCardJson.add("select", accountCardNameJson)
        properties.add("계좌/카드", accountCardJson)
        val nameJson = JsonObject()
        val nameArray = JsonArray()
        val nameTypeJson = JsonObject()
        nameTypeJson.addProperty("type", "text")
        val nameTextJson = JsonObject()
        val nameTextContentJson = JsonObject()
        nameTextContentJson.addProperty("content", name)
        nameTextJson.add("text", nameTextContentJson)
        nameArray.add(nameTextJson)
        nameJson.add("title", nameArray)
        properties.add("이름", nameJson)
        jsonObject.add("properties", properties)
        val params = Gson().toJson(jsonObject)
        logger.info(params)
        try {
            val restTemplate = RestTemplate()
            val headers = HttpHeaders()
            headers["Authorization"] = "Bearer $NOTION_TOKEN"
            headers["Notion-Version"] = "2022-06-28"
            headers["Content-Type"] = MediaType.APPLICATION_JSON_VALUE
            val entity = HttpEntity(params, headers)
            restTemplate.postForObject(CREATE_PAGE_URL, entity, String::class.java)
        } catch (e: Exception) {
            logger.error("Error while sending notion message", e)
        }
    }

    companion object {
        private const val CREATE_PAGE_URL = "https://api.notion.com/v1/pages"
    }
}

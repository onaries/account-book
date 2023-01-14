package com.example.accountbook.model;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TelegramMessage {

    @SerializedName("chat_id")
    private String chatId;
    private String text;

    public TelegramMessage() {
    }

    public TelegramMessage(String chatId, String text) {
        this.chatId = chatId;
        this.text = text;
    }

}


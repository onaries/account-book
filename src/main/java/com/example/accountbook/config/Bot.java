package com.example.accountbook.config;

import com.example.accountbook.entity.StatementDto;
import com.example.accountbook.model.AccountCard;
import com.example.accountbook.model.Category;
import com.example.accountbook.service.AccountCardService;
import com.example.accountbook.service.CategoryService;
import com.example.accountbook.service.StatementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class Bot extends TelegramLongPollingBot {
    private final CategoryService categoryService;
    private final StatementService statementService;

    private final AccountCardService accountCardService;
    private final NotificationManager notificationManager;

    @Value("${telegram.bot.token}")
    private String botToken;


    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public void onUpdateReceived(Update update) {

        Message message = update.getMessage();
        Long chatId = update.getMessage().getChatId();

        String text = message.getText();

        log.info("update: " + text + " from " + message.getChatId().toString());

        switch (text.split(" ")[0]) {
            case "/new": {
                log.info("등록");

                String[] data = text.split(" ");

                if (data.length < 6) {
                    sendMessage(chatId, "잘못된 입력입니다. 다시 입력해주세요.");
                    return;
                }

                String category = data[1];
                String accountCard = data[2];
                String date = data[3];
                String amount = data[4];

                StringBuilder name = new StringBuilder();
                for (int i = 5; i < data.length; i++) {
                    name.append(data[i]).append(" ");
                }

                Optional<Category> categoryEntity = categoryService.getCategoryByName(category);
                if (categoryEntity.isEmpty()) {
                    sendMessage(chatId, "존재하지 않는 종류입니다. 다시 입력해주세요.");
                    return;
                }
                Optional<AccountCard> accountCardEntity = accountCardService.getAccountCardByName(accountCard);
                if (accountCardEntity.isEmpty()) {
                    sendMessage(chatId, "존재하지 않는 카드입니다. 다시 입력해주세요.");
                    return;
                }

                LocalDateTime localDateTime;

                try {
                    localDateTime = LocalDateTime.parse(date);

                } catch (DateTimeParseException e) {
                    sendMessage(chatId, "잘못된 날짜 형식입니다. 다시 입력해주세요.");
                    return;
                }

                StatementDto statementDto = new StatementDto();
                statementDto.setAccountCard(accountCardEntity.get().getId());
                statementDto.setCategory(categoryEntity.get().getId());
                statementDto.setAmount(Integer.parseInt(amount));
                statementDto.setName(name.toString());
                statementDto.setDate(localDateTime);

                statementService.createStatement(statementDto);

                log.info(Arrays.toString(data));

                sendMessage(chatId, "등록되었습니다.");

                break;
            }
            case "/help": {
                log.info("사용법");

                sendMessage(chatId, "/new 종류 계좌(카드) 날짜 금액 내용");

                break;
            }
            case "/category": {
                log.info("category");

//                List<Category> categoryList = categoryService.getCategoryList(s);
//                StringBuilder names = new StringBuilder();
//                for (Category category : categoryList) {
//                    names.append(category.getName()).append(" ");
//                }
//
//                sendMessage(chatId, names.toString());

                break;
            }
            case "/account": {
                log.info("Account");

                PageRequest pageRequest = PageRequest.of(0, 30);

                List<AccountCard> accountCardList = accountCardService.getAccountCardList(pageRequest, "ASC", "id").toList();
                StringBuilder names = new StringBuilder();
                for (AccountCard accountCard : accountCardList) {
                    names.append(accountCard.getName()).append("\n");
                }

                sendMessage(chatId, names.toString());

                break;
            }

            default: {
                break;
            }
        }

    }

    @Override
    public String getBotUsername() {
        return "가계부 봇";
    }

    private void sendMessage(Long chatId, String message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(chatId);

        sendMessage.setText(message);
        sendMessage.enableMarkdown(true);

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            log.info("error: " + e.getMessage());
        }
    }
}

package com.example.accountbook.listener;

import com.example.accountbook.config.NotificationManager;
import com.example.accountbook.model.Category;
import com.example.accountbook.model.Statement;
import com.example.accountbook.repository.StatementRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.persistence.PostPersist;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Component
@RequiredArgsConstructor
public class StatementListener {

    private final Logger logger = LoggerFactory.getLogger(StatementListener.class);

    @Autowired
    private ApplicationContext applicationContext;

    private NotificationManager notificationManager;

    @PostPersist
    public void postPersist(Statement statement) {
        StatementRepository statementRepository = applicationContext.getBean(StatementRepository.class);

        Category category = statement.getCategory();
        int amount = statement.getAmount();
        String name = statement.getName();
        LocalDateTime date = statement.getDate();

        String categoryName = category.getName();
        String mainCategoryName = category.getMainCategory().getName();
        String accountCardName = statement.getAccountCard().getName();

        DecimalFormat formatter = new DecimalFormat("###,###");
        String formattedAmount = formatter.format(amount);

        String dateString = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm").format(date);

        if (category.getType() == Category.TYPE_INCOME) {
            logger.info("msg: " + String.format("💵수입\n[%s] %s\n%s원\n%s\n%s", mainCategoryName, name, formattedAmount, accountCardName, dateString));

//            notificationManager.sendNotification(String.format("💵수입\n%s%s\n%d원\n%s %s", name, categoryName, amount, paymentMethod.getName(), dateString));
        } else if (category.getType() == Category.TYPE_EXPENSE) {

//            if (kind.getCategory().getMainCategory().getWeeklyLimit() > 0) {
//                int weeklyLimit = kind.getCategory().getMainCategory().getWeeklyLimit();
//                int weeklyAmount = statementRepository.sumAmountWeekly(kind.getCategory().getMainCategory().getId(), DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDateTime.now()));
//                int weeklyLeft = weeklyLimit - weeklyAmount;
//
//                log.info("msg: " + String.format("💸지출\n[%s] %s\n%s원\n%s\n%s\n%d원 남음", mainCategoryName, name, formattedAmount , paymentMethod.getName(), dateString, weeklyLeft));
//            } else {
//                log.info("msg: " + String.format("💸지출\n[%s] %s\n%s원\n%s\n%s", mainCategoryName, name, formattedAmount , paymentMethod.getName(), dateString));
//            }

            logger.info("msg: " + String.format("💳지출\n[%s] %s\n%s원\n%s\n%s", mainCategoryName, name, formattedAmount, accountCardName, dateString));

//            notificationManager.sendNotification(String.format("💳지출\n%s%s\n%d원\n%s %s", name, categoryName, amount, accountCardName, dateString));
        } else if (category.getType() == Category.TYPE_SAVE) {

            logger.info("msg: " + String.format("💰저축\n[%s] %s\n%s원\n%s\n%s", mainCategoryName, name, formattedAmount, accountCardName, dateString));

//            notificationManager.sendNotification(String.format("💰저축\n%s%s\n%d원\n%s %s", name, categoryName, amount, paymentMethod.getName(), dateString));
        }

        logger.info("StatementListener: " + statement);


    }

    @Autowired
    public void setNotificationManager(NotificationManager notificationManager) {
        this.notificationManager = notificationManager;
    }

}

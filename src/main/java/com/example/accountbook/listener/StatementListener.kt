package com.example.accountbook.listener

import com.example.accountbook.config.NotificationManager
import com.example.accountbook.entity.StatementPersistEvent
import com.example.accountbook.model.Category
import com.example.accountbook.repository.StatementRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener
import java.text.DecimalFormat
import java.time.format.DateTimeFormatter


@Component
open class StatementListener(
    private val statementRepository: StatementRepository,
    private val notificationManager: NotificationManager
) {

    private val logger = LoggerFactory.getLogger(StatementListener::class.java)

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT, fallbackExecution = true)
    open fun handleStatementPersistEvent(event: StatementPersistEvent) {
        val statement = event.statement

        val formatter = DecimalFormat("###,###")
        val dateString = DateTimeFormatter.ofPattern("yyyy/MM/dd").format(statement.date)

        if (statement.category.type == Category.TYPE_INCOME) {
            logger.info(
                "msg: " + String.format(
                    "💵수입\n[%s-%s] %s\n%s원\n%s\n%s",
                    statement.category.mainCategory.name,
                    statement.category.name,
                    statement.name,
                    formatter.format(statement.amount),
                    statement.accountCard.name,
                    dateString
                )
            )
        } else if (statement.category.type == Category.TYPE_EXPENSE) {
            if (statement.category.mainCategory.weeklyLimit > 0) {
                val weeklyAmount = statementRepository.sumAmountWeekly(statement.category.id, statement.date)[0]
                val weeklyLeft = statement.category.mainCategory.weeklyLimit - weeklyAmount
                logger.info(
                    "msg: " + String.format(
                        "💳지출\n[%s-%s] %s\n%s원\n%s\n%s\n%d원 남음",
                        statement.category.mainCategory.name,
                        statement.category.name,
                        statement.name,
                        formatter.format(statement.amount),
                        statement.accountCard.name,
                        dateString,
                        weeklyLeft
                    )
                );
            } else {
                logger.info(
                    "msg: " + String.format(
                        "\uD83D\uDCB3지출\n[%s-%s] %s\n%s원\n%s\n%s",
                        statement.category.mainCategory.name,
                        statement.category.name,
                        statement.name,
                        formatter.format(statement.amount),
                        statement.accountCard.name,
                        dateString
                    )
                )
            }
        } else if (statement.category.type == Category.TYPE_SAVE) {
            logger.info(
                "msg: " + String.format(
                    "💰저축\n[%s-%s] %s\n%s원\n%s\n%s",
                    statement.category.mainCategory.name,
                    statement.category.name,
                    statement.name,
                    formatter.format(statement.amount),
                    statement.accountCard.name,
                    dateString
                )
            )
        }
    }

}

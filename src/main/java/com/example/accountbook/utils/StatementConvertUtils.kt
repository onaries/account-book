package com.example.accountbook.utils

import com.example.accountbook.model.Category
import com.example.accountbook.model.Statement
import com.example.accountbook.repository.StatementRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.text.DecimalFormat
import java.time.format.DateTimeFormatter

@Component
class StatementConvertUtils(private val statementRepository: StatementRepository) {

    private val logger = LoggerFactory.getLogger(StatementConvertUtils::class.java)

    fun convertMessage(statement: Statement): String {
        val formatter = DecimalFormat("###,###")
        val dateString = DateTimeFormatter.ofPattern("yyyy/MM/dd").format(statement.date)

        var message = ""
        if (statement.category.type == Category.TYPE_INCOME) {
            message = String.format(
                "💵수입\n[%s-%s] %s\n%s원\n%s\n%s",
                statement.category.mainCategory.name,
                statement.category.name,
                statement.name,
                formatter.format(statement.amount),
                statement.accountCard.name,
                dateString
            )
            logger.info("msg: $message")
        } else if (statement.category.type == Category.TYPE_EXPENSE) {
            if (statement.category.mainCategory.weeklyLimit > 0) {
                val weeklyAmount =
                    statementRepository.sumAmountWeekly(statement.category.mainCategory.id, statement.date)[0]
                val weeklyLeft = statement.category.mainCategory.weeklyLimit + weeklyAmount
                message = String.format(
                    "💳지출\n[%s-%s] %s\n%s원\n%s\n%s\n%s원 남음",
                    statement.category.mainCategory.name,
                    statement.category.name,
                    statement.name,
                    formatter.format(statement.amount * -1),
                    statement.accountCard.name,
                    dateString,
                    formatter.format(weeklyLeft)
                )
                logger.info("msg: $message");
            } else {
                message = String.format(
                    "\uD83D\uDCB3지출\n[%s-%s] %s\n%s원\n%s\n%s",
                    statement.category.mainCategory.name,
                    statement.category.name,
                    statement.name,
                    formatter.format(statement.amount * -1),
                    statement.accountCard.name,
                    dateString
                )
                logger.info("msg: $message")
            }
        } else if (statement.category.type == Category.TYPE_SAVE) {
            message = String.format(
                "💰저축\n[%s-%s] %s\n%s원\n%s\n%s",
                statement.category.mainCategory.name,
                statement.category.name,
                statement.name,
                formatter.format(statement.amount * -1),
                statement.accountCard.name,
                dateString
            )
            logger.info("msg: $message")
        }

        return message
    }

}

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
        val formatter2 = DecimalFormat("#.##")
        val dateString = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm").format(statement.date)

        var message = ""
        var monthlyTotalAmount = statementRepository.sumTotalAmountMonthly(
            statement.category.type,
            statement.date
        )[0]
        if (monthlyTotalAmount < 0) {
            monthlyTotalAmount *= -1
        }

        if (statement.category.type == Category.TYPE_INCOME) {

            message = String.format(
                "π΅μμ\n[%s-%s] %s\n%sμ\n%s\n%s\nμ μμ %sμ",
                statement.category.mainCategory.name,
                statement.category.name,
                statement.name,
                formatter.format(statement.amount),
                statement.accountCard.name,
                dateString,
                formatter.format(monthlyTotalAmount)
            )
            logger.info("msg: $message")
        } else if (statement.category.type == Category.TYPE_EXPENSE) {

            if (statement.category.mainCategory.weeklyLimit > 0) {
                val weeklyAmount =
                    statementRepository.sumAmountWeekly(statement.category.mainCategory.id, statement.date)[0]
                val weeklyLeft = statement.category.mainCategory.weeklyLimit + weeklyAmount
                message = String.format(
                    "π³μ§μΆ\n[%s-%s] %s\n%sμ (ν μΈ %sμ %s%%)\n%s\n%s\n%sμ λ¨μ\nμ μ§μΆ %sμ",
                    statement.category.mainCategory.name,
                    statement.category.name,
                    statement.name,
                    formatter.format(statement.amount * -1),
                    formatter.format(statement.discount),
                    formatter2.format(statement.discount.toDouble() / statement.amount * -100),
                    statement.accountCard.name,
                    dateString,
                    formatter.format(weeklyLeft),
                    formatter.format(monthlyTotalAmount)
                )
                logger.info("msg: $message");
            } else {
                message = String.format(
                    "\uD83D\uDCB3μ§μΆ\n[%s-%s] %s\n%sμ (ν μΈ %sμ %s%%)\n%s\n%s\nμ μ§μΆ %sμ",
                    statement.category.mainCategory.name,
                    statement.category.name,
                    statement.name,
                    formatter.format(statement.amount * -1),
                    formatter.format(statement.discount),
                    formatter2.format(statement.discount.toDouble() / statement.amount * -100),
                    statement.accountCard.name,
                    dateString,
                    formatter.format(monthlyTotalAmount)
                )
                logger.info("msg: $message")
            }
        } else if (statement.category.type == Category.TYPE_SAVE) {
            message = String.format(
                "π°μ μΆ\n[%s-%s] %s\n%sμ\n%s\n%s\nμ μ μΆ: %sμ",
                statement.category.mainCategory.name,
                statement.category.name,
                statement.name,
                formatter.format(statement.amount * -1),
                statement.accountCard.name,
                dateString,
                formatter.format(monthlyTotalAmount)
            )
            logger.info("msg: $message")
        }

        return message
    }

}

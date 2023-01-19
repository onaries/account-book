package com.example.accountbook.listener

import com.example.accountbook.config.NotificationManager
import com.example.accountbook.entity.StatementPersistEvent
import com.example.accountbook.utils.StatementConvertUtils
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener


@Component
open class StatementListener(
    private val notificationManager: NotificationManager,
    private val statementConvertUtils: StatementConvertUtils
) {

    private val logger = LoggerFactory.getLogger(StatementListener::class.java)

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT, fallbackExecution = true)
    open fun handleStatementPersistEvent(event: StatementPersistEvent) {
        val statement = event.statement
        val message = statementConvertUtils.convertMessage(statement)

        if (statement.isAlert) {
            notificationManager.sendNotification(message)
        }
    }

}

package com.example.accountbook.service

import com.example.accountbook.entity.StatementDto
import com.example.accountbook.entity.StatementPersistEvent
import com.example.accountbook.model.Statement
import com.example.accountbook.repository.AccountCardRepository
import com.example.accountbook.repository.CategoryRepository
import com.example.accountbook.repository.StatementRepository
import com.example.accountbook.utils.StatementConvertUtils
import com.querydsl.core.Tuple
import org.springframework.context.ApplicationEventPublisher
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*
import javax.transaction.Transactional

@Service
class StatementService(
    private val statementRepository: StatementRepository,
    private val categoryRepository: CategoryRepository,
    private val accountCardRepository: AccountCardRepository,
    private val applicationEventPublisher: ApplicationEventPublisher,
    private val statementConvertUtils: StatementConvertUtils,
) {

    fun getStatementList(
        pageable: Pageable,
        order: String,
        sort: String,
        type: Int?,
        dateGte: String?,
        dateLte: String?,
        categoryId: Long?,
        mainCategoryId: Long?,
    ): Page<Statement> {
        val pageRequest: PageRequest =
            PageRequest.of(pageable.pageNumber - 1, pageable.pageSize, Sort.Direction.valueOf(order), sort)

        var dateGteStr: LocalDateTime? = null
        if (dateGte != null) {
            dateGteStr = LocalDateTime.parse(dateGte + "T00:00")
        }
        var dateLteStr: LocalDateTime? = null
        if (dateLte != null) {
            dateLteStr = LocalDateTime.parse(dateLte + "T23:59")
        }

        return statementRepository.findByCategoryIdAndDateBetween(
            pageRequest,
            type,
            dateGteStr,
            dateLteStr,
            categoryId,
            mainCategoryId,
        )
    }

    fun getStatementSummary(
        pageable: Pageable,
        order: String,
        sort: String,
        type: Int?,
        dateGte: String?,
        dateLte: String?,
        categoryId: Long?,
        mainCategoryId: Long?
    ): HashMap<String, Int> {

        var dateGteStr: LocalDateTime? = null
        if (dateGte != null) {
            dateGteStr = LocalDateTime.parse(dateGte + "T00:00")
        }
        var dateLteStr: LocalDateTime? = null
        if (dateLte != null) {
            dateLteStr = LocalDateTime.parse(dateLte + "T23:59")
        }

        val map = HashMap<String, Int>()
        statementRepository.sumStatementSummary(
            type,
            dateGteStr,
            dateLteStr,
            categoryId,
            mainCategoryId,
        ).forEach { tuple: Tuple ->

            map["amount"] = tuple.toArray()[0] as Int
            map["discount"] = tuple.toArray()[1] as Int
            map["saving"] = tuple.toArray()[2] as Int

        }
        return map
    }

    fun getStatementById(id: Long): Optional<Statement> {
        return statementRepository.findById(id)
    }

    fun countStatement(): Long {
        return statementRepository.countBy()
    }

    @Transactional
    fun createStatement(statementDto: StatementDto): Statement {

        val constructor = Statement::class.java.getConstructor()
        val statement = constructor.newInstance()
        statement.date = statementDto.date
        statement.amount = statementDto.amount
        statement.discount = statementDto.discount
        statement.name = statementDto.name
        statement.isAlert = statementDto.isAlert
        statement.description = statementDto.description
        statement.saving = statementDto.saving

        try {
            statement.category = categoryRepository.findById(statementDto.category).get()
            statement.accountCard = accountCardRepository.findById(statementDto.accountCard).get()
        } catch (e: Exception) {
            throw Exception("???????????? ?????? ????????? ???????????? ????????????.")
        }

        statementRepository.save(statement)
        applicationEventPublisher.publishEvent(StatementPersistEvent(statement))

        return statement
    }

    @Transactional
    fun updateStatement(id: Long, statementDto: StatementDto): Statement {
        val statement = statementRepository.findById(id).get()
        statement.date = statementDto.date
        statement.amount = statementDto.amount
        statement.discount = statementDto.discount
        statement.category = categoryRepository.findById(statementDto.category).get()
        statement.accountCard = accountCardRepository.findById(statementDto.accountCard).get()
        statement.name = statementDto.name
        statement.isAlert = statementDto.isAlert
        statement.description = statementDto.description
        statement.saving = statementDto.saving

        return statement
    }

    fun deleteStatement(id: Long) {
        statementRepository.deleteById(id)
    }

    fun sumStatementWeekly(id: Long, date: LocalDateTime): List<Int> {
        return statementRepository.sumAmountWeekly(id, date)
    }

    fun sumStatementMonthly(id: Long, date: LocalDateTime): List<Int> {
        return statementRepository.sumAmountMonthly(id, date)
    }

    fun sumTotalAmountMonthly(id: Int, date: LocalDateTime): List<Int> {
        return statementRepository.sumTotalAmountMonthly(id, date)
    }

    fun convertStatementMessage(id: Long): String {
        val statement = statementRepository.findById(id).get()
        return statementConvertUtils.convertMessage(statement)
    }

    fun sumStatementGroupByCategoryWeekly(date: LocalDateTime): List<Tuple> {
        return statementRepository.sumAmountWeeklyGroupByCategory(date)
    }

    fun sumStatementDiscountMonthly(date: LocalDateTime): List<Int> {
        return statementRepository.sumTotalDiscountMonthly(date)
    }

    fun sumStatementSavingMonthly(date: LocalDateTime): List<Int> {
        return statementRepository.sumTotalSavingMonthly(date)
    }
}

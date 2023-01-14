package com.example.accountbook.service

import com.example.accountbook.entity.StatementDto
import com.example.accountbook.model.Statement
import com.example.accountbook.repository.AccountCardRepository
import com.example.accountbook.repository.CategoryRepository
import com.example.accountbook.repository.StatementRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import java.util.*
import javax.transaction.Transactional

@Service
open class StatementService(
    private val statementRepository: StatementRepository,
    private val categoryRepository: CategoryRepository,
    private val accountCardRepository: AccountCardRepository
) {

    open fun getStatementList(pageable: Pageable, order: String, sort: String): Page<Statement> {
        val pageRequest: PageRequest =
            PageRequest.of(pageable.pageNumber - 1, pageable.pageSize, Sort.Direction.valueOf(order), sort)

        return statementRepository.findAll(pageRequest)
    }

    open fun getStatementById(id: Long): Optional<Statement> {
        return statementRepository.findById(id)
    }

    open fun countStatement(): Long {
        return statementRepository.countBy()
    }

    open fun createStatement(statementDto: StatementDto): Statement {
        val statement = Statement()

        statement.date = statementDto.date
        statement.amount = statementDto.amount
        statement.discount = statementDto.discount
        statement.category = categoryRepository.findById(statementDto.category).get()
        statement.accountCard = accountCardRepository.findById(statementDto.accountCard).get()
        statement.name = statementDto.name

        return statementRepository.save(statement)
    }

    @Transactional
    open fun updateStatement(id: Long, statementDto: StatementDto): Statement {
        val statement = statementRepository.findById(id).get()
        statement.date = statementDto.date
        statement.amount = statementDto.amount
        statement.discount = statementDto.discount
        statement.category = categoryRepository.findById(statementDto.category).get()
        statement.accountCard = accountCardRepository.findById(statementDto.accountCard).get()
        statement.name = statementDto.name

        return statement
    }

    open fun deleteStatement(id: Long) {
        statementRepository.deleteById(id)
    }
}

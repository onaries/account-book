package com.example.accountbook.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import java.time.LocalDateTime

class StatementRepositoryImpl(private val jpaQueryFactory: JPAQueryFactory) : StatementCustomRepository {
    override fun sumAmountWeekly(mainCategoryId: Long?, date: LocalDateTime?): MutableList<Int> {
        TODO("Not yet implemented")
    }

    override fun sumAmountMonthly(mainCategoryId: Long?, data: LocalDateTime?): MutableList<Int> {
        TODO("Not yet implemented")
    }
}

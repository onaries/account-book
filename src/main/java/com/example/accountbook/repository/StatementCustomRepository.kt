package com.example.accountbook.repository

import com.querydsl.core.Tuple
import java.time.LocalDateTime

interface StatementCustomRepository {
    fun sumAmountWeekly(mainCategoryId: Long, date: LocalDateTime): List<Int>
    fun sumAmountMonthly(mainCategoryId: Long, date: LocalDateTime): List<Int>
    fun sumAmountWeeklyGroupByCategory(date: LocalDateTime?): List<Tuple>
    fun sumTotalAmountMonthly(categoryType: Int?, date: LocalDateTime): List<Int>
    fun sumTotalDiscountMonthly(date: LocalDateTime): List<Int>
    fun sumTotalSavingMonthly(date: LocalDateTime): List<Int>

    fun sumStatementSummary(
        type: Int?,
        dateGte: LocalDateTime?,
        dateLte: LocalDateTime?,
        categoryId: Long?,
        mainCategoryId: Long?
    ): List<Tuple>
}

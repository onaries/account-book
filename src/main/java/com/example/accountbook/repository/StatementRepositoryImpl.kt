package com.example.accountbook.repository

import com.example.accountbook.model.QCategory
import com.example.accountbook.model.QMainCategory
import com.example.accountbook.model.QStatement
import com.querydsl.core.types.ConstantImpl
import com.querydsl.core.types.dsl.Expressions
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
class StatementRepositoryImpl(private val jpaQueryFactory: JPAQueryFactory) : StatementCustomRepository {
    override fun sumAmountWeekly(mainCategoryId: Long, date: LocalDateTime): MutableList<Int> {

        val formattedDate1 = Expressions.dateTimeTemplate(
            LocalDateTime::class.java,
            "DATE_FORMAT({0}, {1})",
            date.minusDays(date.dayOfWeek.value.toLong() + 1),
            ConstantImpl.create("%Y-%m-%d")
        )

        val formattedDate2 = Expressions.dateTimeTemplate(
            LocalDateTime::class.java,
            "DATE_FORMAT({0}, {1})",
            date.plusDays(1),
            ConstantImpl.create("%Y-%m-%d")
        )

        return jpaQueryFactory.select(QStatement.statement.amount.sum().coalesce(0).`as`("sum"))
            .from(QStatement.statement)
            .join(QStatement.statement.category, QCategory.category)
            .join(QCategory.category.mainCategory, QMainCategory.mainCategory)
            .where(
                QMainCategory.mainCategory.id.eq(mainCategoryId),
                QStatement.statement.date.between(formattedDate1, formattedDate2)
            ).fetch();
    }

    override fun sumAmountMonthly(mainCategoryId: Long, date: LocalDateTime): MutableList<Int> {

        return jpaQueryFactory.select(QStatement.statement.amount.sum().coalesce(0).`as`("sum"))
            .from(QStatement.statement)
            .join(QStatement.statement.category, QCategory.category)
            .join(QCategory.category.mainCategory, QMainCategory.mainCategory)
            .where(
                QMainCategory.mainCategory.id.eq(mainCategoryId),
                QStatement.statement.date.month().eq(date.monthValue)
            ).fetch();
    }

    override fun sumTotalAmountMonthly(categoryType: Int, date: LocalDateTime): MutableList<Int> {

        return jpaQueryFactory.select(QStatement.statement.amount.sum().coalesce(0).`as`("sum"))
            .from(QStatement.statement)
            .join(QStatement.statement.category, QCategory.category)
            .where(
                QCategory.category.type.eq(categoryType),
                QStatement.statement.date.month().eq(date.monthValue)
            ).fetch();
    }
}

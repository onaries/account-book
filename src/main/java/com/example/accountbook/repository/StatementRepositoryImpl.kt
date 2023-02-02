package com.example.accountbook.repository

import com.example.accountbook.model.QCategory
import com.example.accountbook.model.QMainCategory
import com.example.accountbook.model.QStatement
import com.example.accountbook.model.Statement
import com.querydsl.core.Tuple
import com.querydsl.core.types.ConstantImpl
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.core.types.dsl.Expressions
import com.querydsl.jpa.impl.JPAQueryFactory
import org.slf4j.LoggerFactory
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import java.time.LocalDateTime


class StatementRepositoryImpl(private val jpaQueryFactory: JPAQueryFactory) :
    QuerydslRepositorySupport(Statement::class.java), StatementCustomRepository {

    private val logger = LoggerFactory.getLogger(StatementRepositoryImpl::class.java)

    override fun sumAmountWeekly(mainCategoryId: Long, date: LocalDateTime): MutableList<Int> {

        val formattedDate1 = Expressions.dateTimeTemplate(
            LocalDateTime::class.java,
            "DATE_FORMAT({0}, {1})",
            date.minusDays(date.dayOfWeek.value.toLong()),
            ConstantImpl.create("%Y-%m-%d")
        )

        val formattedDate2 = Expressions.dateTimeTemplate(
            LocalDateTime::class.java,
            "DATE_FORMAT({0}, {1})",
            date.plusDays(1),
            ConstantImpl.create("%Y-%m-%d")
        )

        logger.info("formattedDate1: $formattedDate1")
        logger.info("formattedDate2: $formattedDate2")

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

    override fun sumTotalAmountMonthly(categoryType: Int?, date: LocalDateTime): MutableList<Int> {

        return jpaQueryFactory.select(QStatement.statement.amount.sum().coalesce(0).`as`("sum"))
            .from(QStatement.statement)
            .join(QStatement.statement.category, QCategory.category)
            .where(
                eqType(categoryType),
                QStatement.statement.date.month().eq(date.monthValue)
            ).fetch();
    }

    override fun sumAmountWeeklyGroupByCategory(date: LocalDateTime?): MutableList<Tuple> {

        return jpaQueryFactory.select(QStatement.statement.amount.sum().coalesce(0).`as`("sum"), QCategory.category.id)
            .from(QStatement.statement)
            .join(QStatement.statement.category, QCategory.category)
            .groupBy(QCategory.category.id)
            .fetch();
    }

    override fun sumTotalDiscountMonthly(date: LocalDateTime): MutableList<Int> {
        return jpaQueryFactory.select(QStatement.statement.discount.sum().coalesce(0).`as`("sum"))
            .from(QStatement.statement)
            .where(
                QStatement.statement.date.month().eq(date.monthValue)
            ).fetch();
    }

    override fun sumTotalSavingMonthly(date: LocalDateTime): MutableList<Int> {
        return jpaQueryFactory.select(QStatement.statement.saving.sum().coalesce(0).`as`("sum"))
            .from(QStatement.statement)
            .where(
                QStatement.statement.date.month().eq(date.monthValue)
            ).fetch();
    }


    override fun sumStatementSummary(
        type: Int?,
        dateGte: LocalDateTime?,
        dateLte: LocalDateTime?,
        categoryId: Long?,
        mainCategoryId: Long?
    ): List<Tuple> {
        return jpaQueryFactory.select(
            QStatement.statement.amount.sum().coalesce(0).`as`("sum_amount"),
            QStatement.statement.discount.sum().coalesce(0).`as`("sum_discount"),
            QStatement.statement.saving.sum().coalesce(0).`as`("sum_saving")
        )
            .from(QStatement.statement)
            .join(QStatement.statement.category, QCategory.category)
            .join(QCategory.category.mainCategory, QMainCategory.mainCategory)
            .where(
                eqType(type),
                eqDateGte(dateGte),
                eqDateLte(dateLte),
                eqCategoryId(categoryId),
                eqMainCategoryId(mainCategoryId)
            ).fetch();
    }

    fun eqType(type: Int?): BooleanExpression? {
        return if (type == null) null else QCategory.category.type.eq(type)
    }

    fun eqDateGte(dateGte: LocalDateTime?): BooleanExpression? {
        return if (dateGte == null) null else QStatement.statement.date.goe(dateGte)
    }

    fun eqDateLte(dateLte: LocalDateTime?): BooleanExpression? {
        return if (dateLte == null) null else QStatement.statement.date.loe(dateLte)
    }

    fun eqCategoryId(categoryId: Long?): BooleanExpression? {
        return if (categoryId == null) null else QCategory.category.id.eq(categoryId)
    }

    fun eqMainCategoryId(mainCategoryId: Long?): BooleanExpression? {
        return if (mainCategoryId == null) null else QMainCategory.mainCategory.id.eq(mainCategoryId)
    }
}



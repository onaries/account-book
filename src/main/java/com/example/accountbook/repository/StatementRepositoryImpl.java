package com.example.accountbook.repository;

import com.example.accountbook.model.QCategory;
import com.example.accountbook.model.QMainCategory;
import com.example.accountbook.model.QStatement;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.Year;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class StatementRepositoryImpl implements StatementCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Integer> sumAmountWeekly(Long mainCategoryId, LocalDateTime date) {
        return queryFactory.select(QStatement.statement.amount.sum()).from(QStatement.statement)
                .leftJoin(QCategory.category).on(QStatement.statement.category.eq(QCategory.category))
                .leftJoin(QMainCategory.mainCategory).on(QCategory.category.mainCategory.eq(QMainCategory.mainCategory))
                .where(QMainCategory.mainCategory.id.eq(mainCategoryId).and(QStatement.statement.date.goe(date.withHour(0).withMinute(0).withSecond(0).withNano(0).minusDays(date.getDayOfWeek().getValue()))).and(QStatement.statement.date.loe(date.withHour(23).withMinute(59).withSecond(59).withNano(999999999).plusDays(1))))
                .fetch();
    }

    @Override
    public List<Integer> sumAmountMonthly(Long mainCategoryId, LocalDateTime date) {
        return queryFactory.select(QStatement.statement.amount.sum()).from(QStatement.statement)
                .leftJoin(QCategory.category).on(QStatement.statement.category.eq(QCategory.category))
                .leftJoin(QMainCategory.mainCategory).on(QCategory.category.mainCategory.eq(QMainCategory.mainCategory))
                .where(QMainCategory.mainCategory.id.eq(mainCategoryId).and(QStatement.statement.date.goe(date.withHour(0).withMinute(0).withSecond(0).withNano(0).withDayOfMonth(1))).and(QStatement.statement.date.loe(date.withHour(23).withMinute(59).withSecond(59).withNano(999999999).withDayOfMonth(date.getMonth().length(Year.isLeap(date.getYear()))))))
                .fetch();

    }
}

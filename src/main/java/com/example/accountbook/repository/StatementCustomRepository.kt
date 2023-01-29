package com.example.accountbook.repository;

import com.querydsl.core.Tuple;

import java.time.LocalDateTime;
import java.util.List;

public interface StatementCustomRepository {

    List<Integer> sumAmountWeekly(Long mainCategoryId, LocalDateTime date);

    List<Integer> sumAmountMonthly(Long mainCategoryId, LocalDateTime date);

    List<Tuple> sumAmountWeeklyGroupByCategory(LocalDateTime date);

    List<Integer> sumTotalAmountMonthly(int categoryType, LocalDateTime date);

    List<Integer> sumTotalDiscountMonthly(LocalDateTime date);

    List<Integer> sumTotalSavingMonthly(LocalDateTime date);

}

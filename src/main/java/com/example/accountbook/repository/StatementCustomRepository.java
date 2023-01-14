package com.example.accountbook.repository;

import java.time.LocalDateTime;
import java.util.List;

public interface StatementCustomRepository {

    List<Integer> sumAmountWeekly(Long mainCategoryId, LocalDateTime date);

    List<Integer> sumAmountMonthly(Long mainCategoryId, LocalDateTime data);
}

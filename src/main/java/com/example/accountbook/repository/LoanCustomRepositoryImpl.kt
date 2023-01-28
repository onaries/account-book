package com.example.accountbook.repository

import com.example.accountbook.model.QLoan
import com.querydsl.jpa.impl.JPAQueryFactory

class LoanCustomRepositoryImpl(
    private val jpaQueryFactory: JPAQueryFactory
) : LoanCustomRepository {

    override fun sumTotalLoans(): List<Int> {
        return jpaQueryFactory.select(QLoan.loan.amount.sum().coalesce(0).`as`("sum"))
            .from(QLoan.loan)
            .fetch();
    }
}

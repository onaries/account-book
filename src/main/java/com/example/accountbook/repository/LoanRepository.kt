package com.example.accountbook.repository

import com.example.accountbook.model.Loan
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


@Repository
interface LoanRepository : JpaRepository<Loan, Long>, LoanCustomRepository {
}

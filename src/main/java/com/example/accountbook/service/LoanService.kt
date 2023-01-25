package com.example.accountbook.service

import com.example.accountbook.entity.LoanDto
import com.example.accountbook.model.Loan
import com.example.accountbook.repository.LoanRepository
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.util.*


@Service
class LoanService(private val loanRepository: LoanRepository) {

    fun getLoans(pageable: Pageable, order: String, sort: String): List<Loan> {
        val pageRequest =
            PageRequest.of(pageable.pageNumber - 1, pageable.pageSize, Sort.Direction.valueOf(order), sort)

        return loanRepository.findAll(pageRequest).toList()
    }

    fun getLoan(id: Long): Optional<Loan> {
        return loanRepository.findById(id)
    }

    fun createLoan(loanDto: LoanDto): Loan {
        val constructor = Loan::class.java.getConstructor()
        val loan = constructor.newInstance() as Loan

        loan.name = loanDto.name
        loan.principal = loanDto.principal
        loan.interestRate = loanDto.interestRate
        loan.totalPeriod = loanDto.totalPeriod
        loan.currentPeriod = loanDto.currentPeriod
        loan.amount = loanDto.amount
        loan.description = loanDto.description

        return loanRepository.save(loan)
    }

    fun updateLoan(id: Long, loanDto: LoanDto): Loan {
        val loan: Loan = loanRepository.findByIdOrNull(id) ?: throw Exception("대출이 존재하지 않습니다.")
        loan.name = loanDto.name
        loan.principal = loanDto.principal
        loan.interestRate = loanDto.interestRate
        loan.totalPeriod = loanDto.totalPeriod
        loan.currentPeriod = loanDto.currentPeriod
        loan.amount = loanDto.amount
        loan.description = loanDto.description

        return loan
    }

    fun deleteLoan(id: Long) {
        val loan: Loan = loanRepository.findByIdOrNull(id) ?: throw Exception("대출이 존재하지 않습니다.")
        loanRepository.delete(loan)
    }

    fun countLoan(): Long {
        return loanRepository.count()
    }

}

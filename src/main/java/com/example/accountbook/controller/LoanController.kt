package com.example.accountbook.controller

import com.example.accountbook.entity.LoanDto
import com.example.accountbook.model.Loan
import com.example.accountbook.service.LoanService
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*


@RestController
class LoanController(private val loanService: LoanService) {

    @GetMapping("/api/loan")
    fun getLoanList(pageable: Pageable, order: String, sort: String): ResponseEntity<List<Loan>> {

        val header = HttpHeaders()
        header.add("X-Total-Count", loanService.countLoan().toString())

        return ResponseEntity.ok().headers(header).body(loanService.getLoans(pageable, order, sort))
    }

    @GetMapping("/api/loan/{id}")
    fun getLoan(@PathVariable id: Long): Optional<Loan> {
        return loanService.getLoan(id)
    }

    @PostMapping("/api/loan")
    fun createLoan(@RequestBody loanDto: LoanDto): Loan {
        return loanService.createLoan(loanDto)
    }

    @PutMapping("/api/loan/{id}")
    fun updateLoan(@PathVariable id: Long, @RequestBody loanDto: LoanDto): Loan {
        return loanService.updateLoan(id, loanDto)
    }

    @DeleteMapping("/api/loan/{id}")
    fun deleteLoan(@PathVariable id: Long): ResponseEntity<Void> {
        loanService.deleteLoan(id)

        return ResponseEntity.status(204).build()
    }

}

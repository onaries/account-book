package com.example.accountbook.controller

import com.example.accountbook.entity.StatementDto
import com.example.accountbook.model.Statement
import com.example.accountbook.service.StatementService
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime
import java.util.*

@RestController
class StatementController(private val statementService: StatementService) {

    private val logger = LoggerFactory.getLogger(StatementController::class.java)

    @GetMapping("/api/statement")
    fun getStatementList(
        pageable: Pageable,
        @RequestParam order: String,
        @RequestParam sort: String,
        @RequestParam type: Int?,
    ): ResponseEntity<List<Statement>> {
        val hearers = HttpHeaders()
        hearers.add("X-Total-Count", statementService.countStatement().toString())

        return ResponseEntity.ok().headers(hearers)
            .body(statementService.getStatementList(pageable, order, sort, type).toList())
    }

    @GetMapping("/api/statement/{id}")
    fun getStatement(@PathVariable id: Long): Optional<Statement> {
        return statementService.getStatementById(id)
    }

    @PostMapping("/api/statement")
    fun postStatement(@RequestBody statementDto: StatementDto): Statement {
        return statementService.createStatement(statementDto)
    }

    @PutMapping("/api/statement/{id}")
    fun putStatement(@PathVariable id: Long, @RequestBody statementDto: StatementDto): Statement {
        return statementService.updateStatement(id, statementDto)
    }

    @DeleteMapping("/api/statement/{id}")
    fun deleteStatement(@PathVariable id: Long) {
        statementService.deleteStatement(id)
    }

    @GetMapping("/api/statement/total")
    fun totalStatement(@RequestParam mode: Int, @RequestParam id: Long, @RequestParam date: String): Int {
        if (mode == 1) {
            val localDateTime = LocalDateTime.parse(date).withHour(0).withMinute(0).withSecond(0)
            return statementService.sumStatementWeekly(id, localDateTime)[0]
        } else if (mode == 2) {
            val localDateTime = LocalDateTime.parse(date)
            return statementService.sumStatementMonthly(id, localDateTime)[0]
        }
        return 0
    }
}

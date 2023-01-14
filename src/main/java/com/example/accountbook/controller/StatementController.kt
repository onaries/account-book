package com.example.accountbook.controller

import com.example.accountbook.entity.StatementDto
import com.example.accountbook.model.Statement
import com.example.accountbook.service.StatementService
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
class StatementController(private val statementService: StatementService) {

    @GetMapping("/api/statement")
    fun getStatementList(
        pageable: Pageable,
        @RequestParam order: String,
        @RequestParam sort: String
    ): ResponseEntity<List<Statement>> {
        val hearers = HttpHeaders()
        hearers.add("X-Total-Count", statementService.countStatement().toString())

        return ResponseEntity.ok().headers(hearers)
            .body(statementService.getStatementList(pageable, order, sort).toList())
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
}

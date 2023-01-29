package com.example.accountbook.controller

import com.example.accountbook.config.NotificationManager
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
class StatementController(
    private val statementService: StatementService,
    private val notificationManager: NotificationManager
) {

    private val logger = LoggerFactory.getLogger(StatementController::class.java)

    @GetMapping("/api/statement")
    fun getStatementList(
        pageable: Pageable,
        @RequestParam order: String,
        @RequestParam sort: String,
        @RequestParam type: Int?,
        @RequestParam(value = "date_gte") dateGte: String?,
        @RequestParam(value = "date_lte") dateLte: String?,
        @RequestParam(value = "category_id") categoryId: Long?,
        @RequestParam(value = "main_category_id") mainCategoryId: Long?,
    ): ResponseEntity<List<Statement>> {
        val hearers = HttpHeaders()

        val statementList = statementService.getStatementList(
            pageable,
            order,
            sort,
            type,
            dateGte,
            dateLte,
            categoryId,
            mainCategoryId
        )

        hearers.add("X-Total-Count", statementList.totalElements.toString())

        return ResponseEntity.ok().headers(hearers)
            .body(statementList.toList())
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
        } else if (mode == 3) {
            val localDateTime = LocalDateTime.parse(date)
            val typeArray = intArrayOf(1, 2, 3)
            var total = 0
            for (i in typeArray) {
                total += statementService.sumTotalAmountMonthly(i, localDateTime)[0]
            }
            return total

        }
        return 0
    }

    @GetMapping("/api/statement/category-total")
    fun totalStatementByCategory(@RequestParam date: String): List<Int> {
        val localDateTime = LocalDateTime.parse(date)
        val typeArray = intArrayOf(1, 2, 3)
        val totalList = mutableListOf<Int>()
        for (i in typeArray) {
            totalList.add(statementService.sumTotalAmountMonthly(i, localDateTime)[0])
        }

        totalList.add(statementService.sumStatementDiscountMonthly(localDateTime)[0])
        totalList.add(statementService.sumStatementSavingMonthly(localDateTime)[0])

        return totalList
    }

    @PostMapping("/api/statement/{id}/alert")
    fun alertStatement(@PathVariable id: Long): String {
        val message = statementService.convertStatementMessage(id)
        notificationManager.sendNotification(message)
        return message
    }

    @GetMapping("/api/statement/group-total")
    fun totalStatementByGroup(@RequestParam date: String): Map<String, Int> {
        val localDateTime = LocalDateTime.parse(date)
        val groupData = statementService.sumStatementGroupByCategoryWeekly(localDateTime)
        val json = mutableMapOf<String, Int>()

        for (data in groupData) {

            val arr = data.toArray()
            json[arr[1].toString()] = arr[0] as Int
        }

        return json
    }

    @GetMapping("/api/statement/summary")
    fun getStatementSummary(
        pageable: Pageable,
        @RequestParam order: String,
        @RequestParam sort: String,
        @RequestParam type: Int?,
        @RequestParam(value = "date_gte") dateGte: String?,
        @RequestParam(value = "date_lte") dateLte: String?,
        @RequestParam(value = "category_id") categoryId: Long?,
        @RequestParam(value = "main_category_id") mainCategoryId: Long?
    ): ResponseEntity<HashMap<String, Int>> {

        val statementList = statementService.getStatementList(
            pageable,
            order,
            sort,
            type,
            dateGte,
            dateLte,
            categoryId,
            mainCategoryId
        )

        val pageList = statementList.toList()
        var pageAmount = 0
        var pageDiscount = 0
        var pageSaving = 0
        for (statement in pageList) {
            pageAmount += statement.amount
            pageDiscount += statement.discount
            pageSaving += statement.saving
        }

        val summary = statementService.getStatementSummary(
            pageable,
            order,
            sort,
            type,
            dateGte,
            dateLte,
            categoryId,
            mainCategoryId
        )

        val map = HashMap<String, Int>();

        map["pageAmount"] = pageAmount
        map["pageDiscount"] = pageDiscount
        map["pageSaving"] = pageSaving
        map["totalAmount"] = summary["amount"] as Int
        map["totalDiscount"] = summary["discount"] as Int
        map["totalSaving"] = summary["saving"] as Int

        return ResponseEntity.ok().body(map)
    }
}

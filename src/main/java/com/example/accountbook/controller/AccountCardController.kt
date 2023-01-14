package com.example.accountbook.controller

import com.example.accountbook.entity.AccountCardDto
import com.example.accountbook.model.AccountCard
import com.example.accountbook.service.AccountCardService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.validation.Valid


@RestController
class AccountCardController(private val accountCardService: AccountCardService) {

    @GetMapping("/api/account-card/all")
    fun getAccountCardList(): ResponseEntity<List<AccountCard>> {

        val hearers = HttpHeaders()
        hearers.add("X-Total-Count", accountCardService.countAccountCard().toString())

        return ResponseEntity.ok().headers(hearers).body(accountCardService.getAccountCardAll())
    }

    @GetMapping("/api/account-card")
    fun getAccountCardListPage(pageable: Pageable, order: String, sort: String): ResponseEntity<List<AccountCard>> {

        val hearers = HttpHeaders()
        hearers.add("X-Total-Count", accountCardService.countAccountCard().toString())

        return ResponseEntity.ok().headers(hearers)
            .body(accountCardService.getAccountCardList(pageable, order, sort).toList())
    }


    @PostMapping("/api/account-card")
    @Operation(summary = "계좌/카드 등록", description = "type: 1-계좌(현금), 2-카드, 3-기타 ")
    fun postAccountCard(@RequestBody accountCardDto: AccountCardDto): AccountCard {
        return accountCardService.createAccountCard(accountCardDto)
    }

    @GetMapping("/api/account-card/{id}")
    fun getAccountCard(@PathVariable id: Long): AccountCard {
        return accountCardService.getAccountCard(id)
    }

    @PutMapping("/api/account-card/{id}")
    fun patchAccountCard(@PathVariable id: Long, @RequestBody @Valid dto: AccountCardDto): AccountCard {
        return accountCardService.updateAccountCard(id, dto)
    }

    @DeleteMapping("/api/account-card/{id}")
    fun deleteAccountCard(@PathVariable id: Long) {
        return accountCardService.deleteAccountCard(id)
    }
}

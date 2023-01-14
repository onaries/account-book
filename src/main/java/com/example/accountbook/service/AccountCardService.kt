package com.example.accountbook.service

import com.example.accountbook.entity.AccountCardDto
import com.example.accountbook.model.AccountCard
import com.example.accountbook.repository.AccountCardRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.util.*
import javax.transaction.Transactional


@Service
open class AccountCardService(private val accountCardRepository: AccountCardRepository) {

    open fun getAccountCardAll(): List<AccountCard> {
        return accountCardRepository.findAll()
    }

    open fun getAccountCardList(pageable: Pageable, order: String, sort: String): Page<AccountCard> {
        val pageRequest =
            PageRequest.of(pageable.pageNumber - 1, pageable.pageSize, Sort.Direction.valueOf(order), sort)

        return accountCardRepository.findAll(pageRequest)
    }

    open fun countAccountCard(): Long {
        return accountCardRepository.countBy()
    }

    open fun getAccountCardByName(name: String): Optional<AccountCard> {
        return accountCardRepository.findByName(name)
    }

    open fun createAccountCard(accountCardDto: AccountCardDto): AccountCard {

        val accountCard: AccountCard = AccountCard()
        accountCard.name = accountCardDto.name
        accountCard.type = accountCardDto.type
        accountCard.amount = accountCardDto.amount
        accountCardRepository.save(accountCard)

        return accountCard
    }

    open fun getAccountCard(id: Long): AccountCard {
        return accountCardRepository.findByIdOrNull(id) ?: throw Exception("계좌/카드가 존재하지 않습니다.")
    }


    @Transactional
    open fun updateAccountCard(id: Long, dto: AccountCardDto): AccountCard {
        val accountCard: AccountCard = accountCardRepository.findByIdOrNull(id) ?: throw Exception("계좌/카드가 존재하지 않습니다.")
        accountCard.name = dto.name
        accountCard.type = dto.type
        accountCard.amount = dto.amount

        return accountCard
    }

    open fun deleteAccountCard(id: Long) {
        val accountCard: AccountCard = accountCardRepository.findByIdOrNull(id) ?: throw Exception("계좌/카드가 존재하지 않습니다.")
        accountCardRepository.delete(accountCard)
    }

}

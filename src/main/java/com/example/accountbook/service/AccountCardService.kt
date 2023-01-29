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
class AccountCardService(private val accountCardRepository: AccountCardRepository) {

    fun getAccountCardAll(): List<AccountCard> {
        return accountCardRepository.findAll()
    }

    fun getAccountCardList(pageable: Pageable, order: String, sort: String): Page<AccountCard> {
        val pageRequest =
            PageRequest.of(pageable.pageNumber - 1, pageable.pageSize, Sort.Direction.valueOf(order), sort)

        return accountCardRepository.findAll(pageRequest)
    }

    fun countAccountCard(): Long {
        return accountCardRepository.countBy()
    }

    fun getAccountCardByName(name: String): Optional<AccountCard> {
        return accountCardRepository.findByName(name)
    }

    fun createAccountCard(accountCardDto: AccountCardDto): AccountCard {

        val constructor = AccountCard::class.java.getConstructor()
        val accountCard = constructor.newInstance() as AccountCard
        accountCard.name = accountCardDto.name
        accountCard.type = accountCardDto.type
        accountCard.amount = accountCardDto.amount
        accountCardRepository.save(accountCard)

        return accountCard
    }

    fun getAccountCard(id: Long): AccountCard {
        return accountCardRepository.findByIdOrNull(id) ?: throw Exception("계좌/카드가 존재하지 않습니다.")
    }


    @Transactional
    fun updateAccountCard(id: Long, dto: AccountCardDto): AccountCard {
        val accountCard: AccountCard = accountCardRepository.findByIdOrNull(id) ?: throw Exception("계좌/카드가 존재하지 않습니다.")
        accountCard.name = dto.name
        accountCard.type = dto.type
        accountCard.amount = dto.amount

        return accountCard
    }

    fun deleteAccountCard(id: Long) {
        val accountCard: AccountCard = accountCardRepository.findByIdOrNull(id) ?: throw Exception("계좌/카드가 존재하지 않습니다.")
        accountCardRepository.delete(accountCard)
    }

}

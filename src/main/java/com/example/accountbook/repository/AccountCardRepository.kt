package com.example.accountbook.repository

import com.example.accountbook.model.AccountCard
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface AccountCardRepository : JpaRepository<AccountCard, Long> {
    fun findByName(name: String): Optional<AccountCard>
    fun countBy(): Long
}

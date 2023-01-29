package com.example.accountbook.repository

import com.example.accountbook.model.MainCategory
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface MainCategoryRepository : JpaRepository<MainCategory, Long> {
    fun findAllByOrderByIdAsc(): List<MainCategory>
    fun findByName(name: String): Optional<MainCategory>
    fun countBy(): Long
}

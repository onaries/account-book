package com.example.accountbook.repository

import com.example.accountbook.model.Category
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface CategoryRepository : JpaRepository<Category, Long> {
    fun findAllByOrderByIdAsc(pageable: Pageable?): List<Category>
    fun findByName(name: String?): Optional<Category>
    fun countBy(): Long
}

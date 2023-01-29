package com.example.accountbook.repository

import com.example.accountbook.model.Statement
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
interface StatementRepository : JpaRepository<Statement, Long>, StatementCustomRepository {
    fun countBy(): Long

    @Query(value = "SELECT s FROM Statement s WHERE (:type IS NULL OR s.category.id IN (SELECT c.id FROM Category c WHERE c.type = :type)) AND (:startDate IS NULL OR s.date >= :startDate) AND (:endDate IS NULL OR s.date <= :endDate) AND (:categoryId IS NULL OR s.category.id = :categoryId) AND (:mainCategoryId IS NULL OR s.category.mainCategory.id = :mainCategoryId) ")
    fun findByCategoryIdAndDateBetween(
        pageable: Pageable,
        @Param("type") type: Int?,
        @Param("startDate") startDate: LocalDateTime?,
        @Param("endDate") endDate: LocalDateTime?,
        @Param("categoryId") categoryId: Long?,
        @Param("mainCategoryId") mainCategoryId: Long?
    ): Page<Statement>
}

package com.example.accountbook.repository

import com.example.accountbook.model.AssetHistory
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import javax.persistence.TypedQuery

@Repository
interface AssetHistoryRepository : JpaRepository<AssetHistory, Long> {

    @Query(value = "SELECT a FROM AssetHistory a WHERE year(a.timestamp) = :year AND month(a.timestamp) = :month ORDER BY a.timestamp DESC")
    fun findPrevAssetHistory(@Param("year") year: Int, @Param("month") month: Int): TypedQuery<AssetHistory>

}

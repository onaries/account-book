package com.example.accountbook.service

import com.example.accountbook.model.AssetHistory
import com.example.accountbook.repository.AssetHistoryRepository
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import java.time.LocalDateTime


@Service
class AssetHistoryService(
    private val assetHistoryRepository: AssetHistoryRepository,
    private val applicationEventPublisher: ApplicationEventPublisher
) {

    fun getPrevAssetHistory(): AssetHistory? {
        val now = LocalDateTime.now()
        val prev = now.minusMonths(1)

        val result = assetHistoryRepository.findPrevAssetHistory(prev.year, prev.monthValue)
        result.maxResults = 1

        return result.singleResult

    }
}

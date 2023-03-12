package com.example.accountbook.controller

import com.example.accountbook.model.AssetHistory
import com.example.accountbook.service.AssetHistoryService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class AssetHistoryController(private val assetHistoryService: AssetHistoryService) {

    @GetMapping("/api/asset-history/prev")
    fun getPrevAssetHistory(): ResponseEntity<AssetHistory?> {
        return ResponseEntity.ok(assetHistoryService.getPrevAssetHistory())
    }

}


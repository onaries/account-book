package com.example.accountbook.controller

import com.example.accountbook.entity.AssetDto
import com.example.accountbook.model.Asset
import com.example.accountbook.service.AssetService
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*


@RestController
class AssetController(private val assetService: AssetService) {

    @GetMapping("/api/asset")
    fun getAssetList(
        pageable: Pageable,
        @RequestParam order: String,
        @RequestParam sort: String
    ): ResponseEntity<List<Asset>> {

        val header = HttpHeaders()
        header.add("X-Total-Count", assetService.countAsset().toString())

        return ResponseEntity.ok().headers(header).body(assetService.getAssets(pageable, order, sort))
    }

    @GetMapping("/api/asset/{id}")
    fun getAsset(@PathVariable id: Long): Optional<Asset> {
        return assetService.getAsset(id)
    }

    @PostMapping("/api/asset")
    fun createAsset(@RequestBody assetDto: AssetDto): Asset {
        return assetService.createAsset(assetDto)
    }

    @PutMapping("/api/asset/{id}")
    fun updateAsset(@PathVariable id: Long, @RequestBody assetDto: AssetDto): Asset {
        return assetService.updateAsset(id, assetDto)
    }

    @DeleteMapping("/api/asset/{id}")
    fun deleteAsset(@PathVariable id: Long): ResponseEntity<Void> {
        assetService.deleteAsset(id)

        return ResponseEntity.status(204).build()
    }


}

package com.example.accountbook.service

import com.example.accountbook.entity.AssetDto
import com.example.accountbook.model.Asset
import com.example.accountbook.repository.AssetRepository
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.util.*
import javax.transaction.Transactional


@Service
class AssetService(private val assetRepository: AssetRepository) {


    fun getAssets(pageable: Pageable, order: String, sort: String): List<Asset> {
        val pageRequest: PageRequest =
            PageRequest.of(pageable.pageNumber - 1, pageable.pageSize, Sort.Direction.valueOf(order), sort)

        return assetRepository.findAll(pageRequest).toList()
    }

    fun getAsset(id: Long): Optional<Asset> {
        return assetRepository.findById(id)
    }

    fun createAsset(assetDto: AssetDto): Asset {

        val constructor = Asset::class.java.getConstructor()
        val asset = constructor.newInstance() as Asset

        asset.name = assetDto.name
        asset.type = assetDto.type
        asset.amount = assetDto.amount
        asset.description = assetDto.description

        assetRepository.save(asset)
        return asset
    }

    @Transactional
    fun updateAsset(id: Long, assetDto: AssetDto): Asset {
        val asset: Asset = assetRepository.findByIdOrNull(id) ?: throw Exception("자산이 존재하지 않습니다.")
        asset.name = assetDto.name
        asset.type = assetDto.type
        asset.amount = assetDto.amount
        asset.description = assetDto.description

        return asset
    }

    fun deleteAsset(id: Long) {
        val asset: Asset = assetRepository.findByIdOrNull(id) ?: throw Exception("자산이 존재하지 않습니다.")
        assetRepository.delete(asset)
    }

    fun countAsset(): Long {
        return assetRepository.count()
    }

    fun getTotalAmount(): List<Int> {
        return assetRepository.sumTotalAssets()
    }
}

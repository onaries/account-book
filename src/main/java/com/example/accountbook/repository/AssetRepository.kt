package com.example.accountbook.repository

import com.example.accountbook.model.Asset
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


@Repository
interface AssetRepository : JpaRepository<Asset, Long>, AssetCustomRepository {

}

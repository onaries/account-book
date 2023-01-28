package com.example.accountbook.repository

import com.example.accountbook.model.Asset
import com.example.accountbook.model.QAsset
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport

class AssetCustomRepositoryImpl(
    private val jpaQueryFactory: JPAQueryFactory
) : QuerydslRepositorySupport(Asset::class.java), AssetCustomRepository {

    override fun sumTotalAssets(): List<Int> {
        return jpaQueryFactory.select(QAsset.asset.amount.sum().coalesce(0).`as`("sum"))
            .from(QAsset.asset)
            .fetch();
    }
}

package com.example.accountbook.model

import org.hibernate.annotations.ColumnDefault
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@EntityListeners(AuditingEntityListener::class)
@Table(
    indexes = [
        Index(name = "idx_asset_updated_at", columnList = "updated_at"),
        Index(columnList = "type")]
)
data class Asset(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long,
    @Column(length = 30, nullable = false) var name: String,
    var type: Int,
    @ColumnDefault("0") var amount: Int,
    var description: String,
    @Column(name = "created_at") @CreatedDate var createdAt: LocalDateTime,
    @Column(name = "updated_at") @LastModifiedDate var updatedAt: LocalDateTime,
)

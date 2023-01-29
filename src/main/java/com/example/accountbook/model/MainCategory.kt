package com.example.accountbook.model

import org.hibernate.annotations.ColumnDefault
import org.hibernate.annotations.DynamicInsert
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
@DynamicInsert
@EntityListeners(AuditingEntityListener::class)
data class MainCategory(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    @Column(unique = true)
    var name: String,
    @CreatedDate
    var createdAt: LocalDateTime,

    @LastModifiedDate
    var updatedAt: LocalDateTime,

    @ColumnDefault("-1")
    var weeklyLimit: @NotNull Int = -1,
)

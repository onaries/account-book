package com.example.accountbook.model

import lombok.NoArgsConstructor
import org.hibernate.annotations.ColumnDefault
import org.hibernate.annotations.DynamicInsert
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
@NoArgsConstructor
@DynamicInsert
@EntityListeners(AuditingEntityListener::class)
data class AccountCard(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    var name: @NotNull String,
    var type: @NotNull Int,

    @ColumnDefault("0")
    var amount: Int,

    @CreatedDate
    var createdAt: LocalDateTime,

    @LastModifiedDate
    var updatedAt: LocalDateTime
)

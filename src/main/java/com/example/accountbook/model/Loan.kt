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
        Index(name = "idx_loan_updated_at", columnList = "updated_at"),
        Index(columnList = "name")]
)
data class Loan(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long,
    @Column(length = 30, nullable = false) var name: String,
    @Column(nullable = false) var principal: Int,
    @Column(name = "interest_rate", nullable = false) var interestRate: Double,
    @Column(name = "total_period", nullable = false) var totalPeriod: Int,
    @Column(name = "current_period", nullable = false) @ColumnDefault("0") var currentPeriod: Int,
    @ColumnDefault("0") var amount: Int,
    var description: String,
    @Column(name = "created_at") @CreatedDate var createdAt: LocalDateTime,
    @Column(name = "updated_at") @LastModifiedDate var updatedAt: LocalDateTime,
)

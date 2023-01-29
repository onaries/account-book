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
@Table(
    indexes = [Index(
        name = "idx_statement_account_card",
        columnList = "account_card_id"
    ), Index(name = "idx_statement_date", columnList = "date")]
)
@EntityListeners(
    AuditingEntityListener::class
)
data class Statement(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    @ManyToOne(optional = false)
    var category: Category,
    var name: @NotNull String,
    var amount: @NotNull Int = 0,

    @ColumnDefault("0")
    var discount: Int = 0,

    @ColumnDefault("0")
    var saving: Int = 0,

    @ManyToOne(optional = false)
    var accountCard: AccountCard,

    @ColumnDefault("false")
    var isAlert: Boolean = false,

    var date: @NotNull LocalDateTime,

    @CreatedDate
    var createdAt: LocalDateTime,

    @LastModifiedDate
    var updatedAt: LocalDateTime,
    var description: String?,
) {
    @PrePersist
    fun prePersist() {
        if (category.type != Category.TYPE_INCOME) {
            amount *= -1
        }
    }
}

package com.example.accountbook.model

import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
@Table(uniqueConstraints = [UniqueConstraint(columnNames = ["name", "main_category_id"])])
data class Category(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    @ManyToOne(optional = false)
    var mainCategory: MainCategory,
    var name: @NotNull String,
    var type: @NotNull Int = 0
) {
    companion object {
        const val TYPE_INCOME = 1
        const val TYPE_EXPENSE = 2
        const val TYPE_SAVE = 3
    }
}

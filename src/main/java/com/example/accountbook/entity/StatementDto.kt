package com.example.accountbook.entity

import java.time.LocalDateTime


data class StatementDto(
    var category: Long = 0,
    var name: String = "",
    var amount: Int = 0,
    var discount: Int = 0,
    var accountCard: Long = 0,
    var date: LocalDateTime = LocalDateTime.now(),
    var isAlert: Boolean = false,
    var description: String? = ""
)

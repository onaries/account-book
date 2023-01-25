package com.example.accountbook.entity

data class LoanDto(
    val name: String = "",
    val principal: Int = 0,
    val interestRate: Double = 0.0,
    val totalPeriod: Int = 0,
    val currentPeriod: Int = 0,
    val amount: Int = 0,
    val description: String = "",
)

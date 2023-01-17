package com.example.accountbook.entity

import com.example.accountbook.model.Statement

data class StatementPersistEvent(
    val statement: Statement
)

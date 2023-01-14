package com.example.accountbook.entity

import org.openapitools.jackson.nullable.JsonNullable

data class AccountCardUpdateDto(
        val name: JsonNullable<String> = JsonNullable.undefined(),
        val type: JsonNullable<Int> = JsonNullable.undefined(),
        val amount: JsonNullable<Int> = JsonNullable.undefined()) {}

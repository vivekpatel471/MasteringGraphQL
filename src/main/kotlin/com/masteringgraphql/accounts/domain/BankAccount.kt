package com.masteringgraphql.accounts.domain


data class BankAccount(
    val id: String,
    val clientId: String,
    val currency: Currency,
    val balance: Float,
    val status: String
)

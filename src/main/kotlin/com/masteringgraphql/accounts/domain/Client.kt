package com.masteringgraphql.accounts.domain

data class Client(val id: String, val accountId: String, val firstName: String, val middleName: String?, val lastName: String)


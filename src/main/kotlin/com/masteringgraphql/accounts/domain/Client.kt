package com.masteringgraphql.accounts.domain

@JvmRecord
data class Client(
    val id : String ,
    val accountId : Int ,
    val firstName : String ,
    val middleName : String ,
    val lastName : String
) 
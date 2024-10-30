package com.masteringgraphql.accounts.repo

import com.masteringgraphql.accounts.entity.BankAccount
import org.springframework.data.jpa.repository.JpaRepository

interface BankAccountRepo : JpaRepository<BankAccount , Int> {
    fun findByStatus(accountStatus : String) : List<BankAccount> ;
}


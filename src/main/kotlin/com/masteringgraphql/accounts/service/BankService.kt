package com.masteringgraphql.accounts.service

import com.masteringgraphql.accounts.domain.BankAccount
import com.masteringgraphql.accounts.domain.Currency
import com.sun.jdi.IntegerValue
import org.springframework.stereotype.Service
import java.lang.Integer.*


@Service
class BankService {

    // Immutable lists for bank accounts and clients
     val bankAccounts: List<BankAccount> = listOf(
        BankAccount(100, "C100", Currency.USD, 106.00f, "A"),
        BankAccount(101, "C200", Currency.CAD, 250.00f, "A"),
        BankAccount(102, "C300", Currency.CAD, 333.00f, "I"),
        BankAccount(103, "C400", Currency.EUR, 4000.00f, "A"),
        BankAccount(104, "C500", Currency.EUR, 4000.00f, "A")
    )

    // Method to get all bank accounts
    fun getAccounts(): List<BankAccount> {
        return bankAccounts
    }
}



package com.masteringgraphql.accounts.service

import com.masteringgraphql.accounts.domain.BankAccount
import com.masteringgraphql.accounts.domain.Client
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
     val clients: List<Client> = listOf(
        Client("C100", 100, "Elena", "Maria", "Gonzalez"),
        Client("C200", 101, "James", "Robert", "Smith"),
        Client("C300", 102, "Aarav", "Kumar", "Patel"),
        Client("C400", 103, "Linh", "Thi", "Nguyen"),
        Client("C500", 104, "Olivia", "Grace", "Johnson")
    )

    // Method to get all bank accounts
    fun getAccounts(): List<BankAccount> {
        return bankAccounts
    }

    // Method to get client by account ID
    fun getClientByAccountId(accountId: Int): Client? {
        return clients.firstOrNull { it.accountId == accountId }
    }

    fun getClients(accountIds :MutableSet<Int>) : MutableMap<Int , Client> {
        val accountToClients : MutableMap<Int, Client> = HashMap()

        for (accountId in accountIds) {
            // Search for clients with the matching account ID and add them to the list

            for (client in clients) {
                if (client.accountId === accountId)
                    accountToClients[accountId] = client
            }
        }

        return accountToClients
    }
}



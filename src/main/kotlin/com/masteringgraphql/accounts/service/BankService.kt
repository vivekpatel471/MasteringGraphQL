package com.masteringgraphql.accounts.service

import com.masteringgraphql.accounts.domain.BankAccount
import com.masteringgraphql.accounts.domain.Client
import com.masteringgraphql.accounts.domain.Currency
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class BankService {

    // Immutable lists for bank accounts and clients
     val bankAccounts: List<BankAccount> = listOf(
        BankAccount("A100", "C100", Currency.USD, 106.00f, "A"),
        BankAccount("A101", "C200", Currency.CAD, 250.00f, "A"),
        BankAccount("A102", "C300", Currency.CAD, 333.00f, "I"),
        BankAccount("A103", "C400", Currency.EUR, 4000.00f, "A"),
        BankAccount("A104", "C500", Currency.EUR, 4000.00f, "A")
    )
     val clients: List<Client> = listOf(
        Client("C100", "A100", "Elena", "Maria", "Gonzalez"),
        Client("C200", "A101", "James", "Robert", "Smith"),
        Client("C300", "A102", "Aarav", "Kumar", "Patel"),
        Client("C400", "A103", "Linh", "Thi", "Nguyen"),
        Client("C500", "A104", "Olivia", "Grace", "Johnson")
    )

    // Method to get all bank accounts
    fun getAccounts(): List<BankAccount> {
        return bankAccounts
    }

    // Method to get client by account ID
    fun getClientByAccountId(accountId: String): Client? {
        return clients.firstOrNull { it.accountId == accountId }
    }
}


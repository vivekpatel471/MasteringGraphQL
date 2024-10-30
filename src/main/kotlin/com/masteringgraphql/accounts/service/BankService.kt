package com.masteringgraphql.accounts.service

import com.masteringgraphql.accounts.domain.Client
import com.masteringgraphql.accounts.entity.BankAccount
import com.masteringgraphql.accounts.exceptions.AccountNotFoundException
import com.masteringgraphql.accounts.exceptions.ClientNotFoundException
import com.masteringgraphql.accounts.repo.BankAccountRepo
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class BankService {

    private val logger = LoggerFactory.getLogger(BankService::class.java)

    companion object {
        private val clients = listOf(
            Client(100L, "John", "T.", "Doe") ,
            Client(101L, "Emma", "B.", "Smith"),
            Client(102L, "James", "R.", "Brown"),
            Client(103L, "Olivia", "S.", "Johnson"),
            Client(104L, "William", "K.", "Jones")
        )
    }

    @Autowired
    private lateinit var repo: BankAccountRepo

    fun save(account: BankAccount) {
        if (isValidClient(account)) {
            repo.save(account)
        } else {
            throw ClientNotFoundException("Client Not Found ${account.clientId}")
        }
    }

    fun modify(account: BankAccount): BankAccount {
        return if (isValidClient(account)) {
            repo.save(account)
        } else {
            throw ClientNotFoundException("Client Not Found ${account.clientId}")
        }
    }

    fun getAccounts(accountStatus:String): List<BankAccount> = repo.findByStatus(accountStatus)

    fun accountById(accountId: Int): BankAccount {
        return repo.findById(accountId)
            .orElseThrow { AccountNotFoundException("Account Not Found $accountId") }
    }

    fun delete(accountId: Int): Boolean {
        return repo.findById(accountId)
            .map { account ->
                repo.delete(account)
                true
            }
            .orElse(false)
    }

    private fun getClients(): List<Client> = clients

    fun getBankAccountClientMap(bankAccounts: List<BankAccount>): Map<BankAccount, Client> {
        // Collect all client IDs from the list of bank accounts
        val clientIds = bankAccounts.map { it.clientId }.toSet()

        // Fetch clients for all collected IDs
        val relevantClients = getClients().filter { it.id in clientIds }

        // Map each bank account to its corresponding client
        return bankAccounts.associateWith { bankAccount ->
            relevantClients.find { it.id == bankAccount.clientId }
                ?: throw ClientNotFoundException("Client Not Found for Account ${bankAccount.id}")
        }
    }

    private fun isValidClient(account: BankAccount): Boolean {
        return clients.any { it.id == account.clientId }
    }
}


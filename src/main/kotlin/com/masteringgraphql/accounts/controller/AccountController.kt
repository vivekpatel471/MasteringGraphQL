package com.masteringgraphql.accounts.controller


import com.masteringgraphql.accounts.domain.BankAccount
import com.masteringgraphql.accounts.domain.Client
import com.masteringgraphql.accounts.service.BankService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.graphql.data.method.annotation.SchemaMapping
import org.springframework.stereotype.Controller

@Controller
class AccountsController {

    @Autowired
    lateinit var bankService: BankService

    val log = LoggerFactory.getLogger(AccountsController::class.java)

    @QueryMapping("accounts")
    fun accounts(): List<BankAccount> {
       log.info("Getting Accounts")
        return bankService.getAccounts()
    }

    @SchemaMapping(typeName = "BankAccount", field = "client")
    fun getClient(account: BankAccount): Client? {
        log.info("Getting client for ${account.id}")
        return bankService.getClientByAccountId(account.id)
    }
}


package com.masteringgraphql.accounts.datafetcher


import com.masteringgraphql.accounts.domain.BankAccount
import com.masteringgraphql.accounts.service.BankService
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsQuery
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired


@DgsComponent
class AccountsDataFetcher {

    @Autowired
    lateinit var bankService: BankService

    val log = LoggerFactory.getLogger(AccountsDataFetcher::class.java)

    @DgsQuery
    fun accounts(): List<BankAccount> {
        log.info("Getting Accounts")
        return bankService.getAccounts()
    }

}
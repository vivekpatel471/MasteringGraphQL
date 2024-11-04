package com.masteringgraphql.accounts.datafetcher

import com.masteringgraphql.accounts.controller.AccountsController
import com.masteringgraphql.accounts.dataloader.ClientDataLoader
import com.masteringgraphql.accounts.domain.BankAccount
import com.masteringgraphql.accounts.domain.Client
import com.masteringgraphql.accounts.service.BankService
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsData
import com.netflix.graphql.dgs.DgsDataFetchingEnvironment
import com.netflix.graphql.dgs.DgsQuery
import org.dataloader.DataLoader
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import java.util.concurrent.CompletableFuture


@DgsComponent
class AccountsDataFetcher {

    @Autowired
    lateinit var bankService: BankService

    val log = LoggerFactory.getLogger(AccountsController::class.java)

    @DgsQuery
    fun accounts(): List<BankAccount> {
        log.info("Getting Accounts")
        return bankService.getAccounts()
    }

    @DgsData(parentType = "Account" , field = "client")
    fun client(dfe : DgsDataFetchingEnvironment) : CompletableFuture<Client>? {
        val clientsDataLoader = dfe.getDataLoader<Int , Client>(
            ClientDataLoader::class.java
        )

        //Because the client field is on Account, the getSource() method will return the Account instance.
        val account : BankAccount? = dfe.getSource<BankAccount>()

        if (account != null) {
            log.info("Get Clients for Account " + account.id)
                return clientsDataLoader.load(account.id)
        }
        return null
    }
}
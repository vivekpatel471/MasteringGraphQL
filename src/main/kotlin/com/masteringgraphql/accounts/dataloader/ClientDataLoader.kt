package com.masteringgraphql.accounts.dataloader

import com.masteringgraphql.accounts.controller.AccountsController
import com.masteringgraphql.accounts.domain.Client
import com.masteringgraphql.accounts.service.BankService
import com.netflix.graphql.dgs.DgsDataLoader
import org.dataloader.MappedBatchLoader
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import java.util.concurrent.CompletableFuture
import java.util.concurrent.CompletableFuture.supplyAsync
import java.util.concurrent.CompletionStage
import java.util.function.Supplier


@DgsDataLoader(name = "client")
class ClientDataLoader : MappedBatchLoader<Int , Client> {

    @Autowired
    var accountsService : BankService? = null

    val log = LoggerFactory.getLogger(AccountsController::class.java)



     override fun load(p0 : MutableSet<Int>?) : CompletionStage<MutableMap<Int , Client>>? {
         return supplyAsync<MutableMap<Int , Client>>(
             Supplier<MutableMap<Int , Client>> { p0?.let { accountsService!!.getClients(it) } })
     }


}
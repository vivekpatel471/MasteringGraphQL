package com.accounts.datafetcher


import com.accounts.domain.BankAccount
import com.masteringgraphql.accounts.service.BankService
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsData
import com.netflix.graphql.dgs.DgsDataFetchingEnvironment
import com.netflix.graphql.dgs.DgsEntityFetcher
import org.springframework.beans.factory.annotation.Autowired

@DgsComponent
class ClientDataFetcher {
    @Autowired
    var accountsService : BankService? = null

    @DgsData(parentType = "BankAccount" , field = "client")
    fun clients(dfe : DgsDataFetchingEnvironment) : MutableList<Any>? {
        val account = dfe.getSource<BankAccount>()
        return accountsService?.getClients(account!!.id)
    }

    @DgsEntityFetcher(name = "BankAccount")
    fun account(values : Map<String? , Any?>) : BankAccount {
        val accountId = values["id"]

        return if (accountId is Number) {
            BankAccount(accountId.toInt() , null)
        } else if (accountId is String) {
            BankAccount(accountId.toInt() , null)
        } else {
            throw IllegalArgumentException("Object is not a Number")
        }
    }
}
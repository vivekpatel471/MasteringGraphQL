package com.masteringgraphql.accounts.controller


import com.masteringgraphql.accounts.service.BankService
import com.masteringgraphql.accounts.domain.Client
import com.masteringgraphql.accounts.entity.BankAccount

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.graphql.data.method.annotation.*
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
/*query myQuery {
  accounts {
    status
    balance
    currency
    client {
      firstName
      id
      lastName
      middleName
    }
    id
  }
}*/
        @QueryMapping
        fun accountById(@Argument("accountId") accountId: Int): BankAccount {
            log.info("Getting Account ")
            return bankService.accountById(accountId)
        }
/*query  myQuery{

  accountById(accountId :10){
    status
    balance
  }
}*/
        @BatchMapping(field = "client", typeName = "BankAccountType")
        fun clients(bankAccounts: List<BankAccount>): Map<BankAccount, Client> {
            log.info("Getting client for Accounts : ${bankAccounts.size}")
            return bankService.getBankAccountClientMap(bankAccounts)
        }

        @MutationMapping
        fun addAccount(@Argument("account") account: BankAccount): Boolean {
            log.info("Saving Account : $account")
            bankService.save(account)
            return true
        }
    /*mutation myMutation{

  addAccount(account :{
    id:1012,
    clientId:102,
    currency:USD,
    balance:101990.0,
    status:"Active"

  })
}*/

        @MutationMapping
        fun editAccount(@Argument("account") account: BankAccount): BankAccount {
            log.info("Editing Account : $account")
            return bankService.modify(account)
        }
/*mutation myMutation{

  editAccount(account :{
    id:1012,
    clientId:102,
    currency:USD,
    balance:101990.0,
    status:"inActive"

  }) {
    id
  }
}*/
        @MutationMapping
        fun deleteAccount(@Argument("id") accountId: Int): Boolean {
            log.info("Deleting Account : $accountId")
            return bankService.delete(accountId)
        }
    }
/*mutation myMutation{

  deleteAccount(id :1012)
}*/


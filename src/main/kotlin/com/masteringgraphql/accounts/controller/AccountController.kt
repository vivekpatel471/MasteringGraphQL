package com.masteringgraphql.accounts.controller


import com.masteringgraphql.accounts.service.BankService
import com.masteringgraphql.accounts.domain.Client
import com.masteringgraphql.accounts.entity.BankAccount
import com.masteringgraphql.accounts.exceptions.ClientNotFoundException
import graphql.GraphQLError
import graphql.schema.DataFetchingEnvironment

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.graphql.data.method.annotation.*
import org.springframework.graphql.execution.ErrorType
import org.springframework.stereotype.Controller

@Controller
class AccountsController {

    @Autowired
    lateinit var bankService: BankService

    val log = LoggerFactory.getLogger(AccountsController::class.java)

    @QueryMapping("accounts")
    fun accounts(@ContextValue accountStatus: String): List<BankAccount> {
       log.info("Getting Accounts")
        return bankService.getAccounts(accountStatus)
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
       /*    mutation myMutation{

  addAccount(account :{
    id: 123
    currency: USD
    country: USA
    balance: 190878.90
    status: "ACtive"
    transferLimit:  50000
    accountCreateDate: "1024-10-29T16:39:57-08:00"})

    }
    {
    "errors": [
        {
            "message": "Validation error (WrongType@[addAccount]) : argument 'account.country' with value 'EnumValue{name='USA'}' is not a valid 'CountryCode' - Expected AST type 'StringValue' but was 'EnumValue'.",
            "locations": [
                {
                    "line": 3,
                    "column": 14
                }
            ],
            "extensions": {
                "classification": "ValidationError"
            }
        }
    ]
}
    mutation myMutation{

  addAccount(account :{
    id: 123
    currency: USD
    clientId :102
    country: "AE"
    balance: 190878.90
    status: "ACtive"
    transferLimit:  50000
    accountCreateDate: "1024-10-29T16:39:57-08:00"})

    }
    {
    "data": {
        "addAccount": true
    }
}
    */
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
    /*mutation myMutation{

  deleteAccount(id :1012)
}*/

    @GraphQlExceptionHandler
    fun handle(ex: ClientNotFoundException , environment: DataFetchingEnvironment): GraphQLError {
        return GraphQLError.newError()
            .errorType(ErrorType.BAD_REQUEST)
            .message("Unable to locate the specified client. " +
                    "Please verify the client details and attempt your request again. : " +
                    ex.message)
            .path(environment.executionStepInfo.path)
            .location(environment.field.sourceLocation)
            .build()
    }
/*    mutation myMutation{

  addAccount(account :{
    id: 123
    currency: USD
    clientId :1020
    country: "AE"
    balance: 190878.90
    status: "ACtive"
    transferLimit:  50000
    accountCreateDate: "1024-10-29T16:39:57-08:00"})

    }
    {
    "errors": [
        {
            "message": "Unable to locate the specified client. Please verify the client details and attempt your request again. : Client Not Found 1020",
            "locations": [
                {
                    "line": 3,
                    "column": 3
                }
            ],
            "path": [
                "addAccount"
            ],
            "extensions": {
                "classification": "BAD_REQUEST"
            }
        }
    ],
    "data": {
        "addAccount": null
    }
}

    */

    }



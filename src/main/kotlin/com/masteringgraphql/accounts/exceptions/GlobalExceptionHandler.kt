package com.masteringgraphql.accounts.exceptions

import graphql.GraphQLError
import graphql.schema.DataFetchingEnvironment
import org.springframework.graphql.data.method.annotation.GraphQlExceptionHandler
import org.springframework.graphql.execution.ErrorType
import org.springframework.web.bind.annotation.ControllerAdvice
import java.time.Instant

@ControllerAdvice
class GlobalExceptionHandler {
    private val extMap = mutableMapOf<String, Any>()

    @GraphQlExceptionHandler
    fun handle(ex: AccountNotFoundException, environment: DataFetchingEnvironment): GraphQLError {
        extMap["errorCode"] = "ACCOUNT_NOT_FOUND"
        extMap["userMessage"] = "The account you are trying to access does not exist."
        extMap["timestamp"] = Instant.now().toString()
        extMap["actionableSteps"] = "Please verify the account ID and try again."

        return GraphQLError.newError()
            .errorType(ErrorType.BAD_REQUEST)
            .message("Message from GLOBAL exception handler : ${ex.message}")
            .path(environment.executionStepInfo.path)
            .location(environment.field.sourceLocation)
            .extensions(extMap)
            .build()
    }

    /*
    *     query myQuery{

            accountById(accountId :19191 (wrong id)){
             ...AccountDetils
            }

    }

    fragment AccountDetils on BankAccountType{
  id
    client{
        firstName
        country
    }
    currency
    country
    balance
    status
    transferLimit
    accountCreateDate


    }
    {
    "errors": [
        {
            "message": "Message from GLOBAL exception handler : Account Not Found 19191",
            "locations": [
                {
                    "line": 3,
                    "column": 13
                }
            ],
            "path": [
                "accountById"
            ],
            "extensions": {
                "errorCode": "ACCOUNT_NOT_FOUND",
                "userMessage": "The account you are trying to access does not exist.",
                "timestamp": "2024-10-29T08:57:23.717732Z",
                "actionableSteps": "Please verify the account ID and try again.",
                "classification": "BAD_REQUEST"
            }
        }
    ],
    "data": {
        "accountById": null
    }
}
    * */
}


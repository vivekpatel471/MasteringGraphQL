package com.masteringgraphql.accounts.intersaptor

import graphql.GraphQLError
import graphql.GraphqlErrorBuilder
import org.springframework.graphql.ResponseError
import org.springframework.graphql.execution.ErrorType
import org.springframework.graphql.server.WebGraphQlInterceptor
import org.springframework.graphql.server.WebGraphQlRequest
import org.springframework.graphql.server.WebGraphQlResponse
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class RequestErrorInterceptor : WebGraphQlInterceptor {

    override fun intercept(request: WebGraphQlRequest, chain: WebGraphQlInterceptor.Chain): Mono<WebGraphQlResponse> {
        return chain.next(request)
            .map(this::processResponse)
    }

    private fun processResponse(response: WebGraphQlResponse): WebGraphQlResponse {
        return if (response.isValid) {
            response
        } else {
            val modifiedErrors = modifyErrors(response.errors)
            response.transform { builder -> builder.errors(modifiedErrors).build() }
        }
    }

    private fun modifyErrors(originalErrors: List<ResponseError>): List<GraphQLError> {
        return originalErrors.map(this::createValidationError)
    }

    private fun createValidationError(error: ResponseError): GraphQLError {
        val errorMessage: String
        val extensionMap = mutableMapOf<String, Any>()
        when {
            error.message?.contains("is not a valid 'CountryCode'") == true -> {
                errorMessage = "Invalid country code. Use a supported country code."
                extensionMap["Supported Country Codes"] = "International country codes are short alphanumeric combinations that uniquely identify countries or geographical areas around the world."
            }
            error.message?.contains("is not a valid 'Currency'") == true -> {
                errorMessage = "Invalid Currency code. Use a supported Currency code."
                extensionMap["Supported Currency Codes"] = "USD, CAD, EUR"
            }
            else -> errorMessage = error.message.toString()
        }

        return GraphqlErrorBuilder.newError()
            .message(errorMessage)
            .errorType(ErrorType.BAD_REQUEST)
            .extensions(extensionMap)
            .locations(error.locations)
            // Add more customization to the error as needed
            .build()
    }
}


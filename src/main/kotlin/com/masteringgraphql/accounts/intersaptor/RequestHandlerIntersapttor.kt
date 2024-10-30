package com.masteringgraphql.accounts.intersaptor

import org.springframework.graphql.server.WebGraphQlInterceptor
import org.springframework.graphql.server.WebGraphQlRequest
import org.springframework.graphql.server.WebGraphQlResponse
import org.springframework.stereotype.Component
import org.springframework.util.ObjectUtils
import reactor.core.publisher.Mono

@Component
class RequestHandlerIntersapttor : WebGraphQlInterceptor {
    override fun intercept(
        request : WebGraphQlRequest ,
        chain : WebGraphQlInterceptor.Chain
    ) : Mono<WebGraphQlResponse> {
            var value = request.headers.getFirst("accountStatus")
        if (value != null) {
            request.configureExecutionInput { executionInput , builder ->
                builder.graphQLContext(mapOf("accountStatus" to value)).build()
            }
        }
        return chain.next(request)
    }
}
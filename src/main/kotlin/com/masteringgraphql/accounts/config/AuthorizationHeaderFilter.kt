package com.masteringgraphql.accounts.config

import jakarta.servlet.FilterChain
import jakarta.servlet.GenericFilter
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component

@Component
class AuthorizationHeaderFilter : GenericFilter() {
    override fun doFilter(request : ServletRequest , response : ServletResponse , chain : FilterChain) {
        val httpRequest = request as HttpServletRequest
        val httpResponse = response as HttpServletResponse

        val authHeader = httpRequest.getHeader("Authorization")

        if (authHeader == null || authHeader.isEmpty()) {
            httpResponse.status = HttpServletResponse.SC_UNAUTHORIZED
            httpResponse.writer.write("Authorization header is missing")
            return
        }

        chain.doFilter(request , response)
    }
}
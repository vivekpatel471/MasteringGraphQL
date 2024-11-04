package com.masteringgraphql.accounts.config

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component

@Component
class AuthEntryPointBasicAuth : AuthenticationEntryPoint {

    private val logger : org.slf4j.Logger? = LoggerFactory.getLogger(AuthEntryPointBasicAuth::class.java)

    override fun commence(
        request : HttpServletRequest? ,
        response : HttpServletResponse? ,
        authException : AuthenticationException?
    ) {

        val authHeader = request!!.getHeader("Authorization")
        if (authHeader == null || authHeader.isEmpty()) {
            logger!!.error("Missing Authorization header")

            response!!.contentType = MediaType.APPLICATION_JSON_VALUE
            response!!.status = HttpServletResponse.SC_UNAUTHORIZED

            val body : MutableMap<String , Any> = HashMap()
            body["status"] = HttpServletResponse.SC_UNAUTHORIZED
            body["error"] = "Unauthorized"
            body["message"] = "Authorization header is missing in the request"
            body["path"] = request!!.servletPath

            val mapper = ObjectMapper()
            mapper.writeValue(response!!.outputStream , body)
            return
        }
        if (authException != null) {
            logger!!.error("Unauthorized error: {}" , authException.message)
        }

        response!!.contentType = MediaType.APPLICATION_JSON_VALUE
        response!!.status = HttpServletResponse.SC_UNAUTHORIZED

        val body : MutableMap<String , Any> = HashMap()
        body["status"] = HttpServletResponse.SC_UNAUTHORIZED
        body["error"] = "Unauthorized"
        body["message"] = authException?.message ?:" "
        body["path"] = request.servletPath

        val mapper = ObjectMapper()
        mapper.writeValue(response!!.outputStream , body)

    }
}
package com.masteringgraphql.accounts.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer
import org.springframework.security.config.Customizer.withDefaults
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer
import org.springframework.security.config.annotation.web.configurers.ExceptionHandlingConfigurer
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
class SecurityConfig {
    @Autowired
    private val unauthorizedHandler : AuthEntryPointBasicAuth? = null

    @Value("\${app.user.admin}")
    private val adminUser : String? = null

    @Value("\${app.user.admin.password}")
    private val adminPassword : String? = null

    @Value("\${app.user.user}")
    private val appUser : String? = null

    @Value("\${app.user.user.password}")
    private val appUserPassword : String? = null

    @Bean
    @Throws(Exception::class)
    fun defaultSecurityFilterChain(http : HttpSecurity) : SecurityFilterChain {
        http.csrf { csrf : CsrfConfigurer<HttpSecurity> -> csrf.disable() }

        // Add a custom filter to check for the Authorization header
        http.addFilterBefore(AuthorizationHeaderFilter() , BasicAuthenticationFilter::class.java)

        http.authorizeHttpRequests{authorizeRequests  ->
            authorizeRequests
                .requestMatchers("/graphql").permitAll()
                .anyRequest().authenticated()
        }


        http.exceptionHandling(Customizer { exception : ExceptionHandlingConfigurer<HttpSecurity?> ->
            exception.authenticationEntryPoint(
                unauthorizedHandler
            )
        })

        http.httpBasic(withDefaults())

        return http.build()
    }

    @Bean
    fun userDetailsService() : UserDetailsService {
        val user1 = User.withUsername(appUser)
            .password(passwordEncoder().encode(appUserPassword))
            .roles("USER")
            .build()

        val user2 = User.withUsername(adminUser)
            .password(passwordEncoder().encode(adminPassword))
            .roles("ADMIN")
            .build()

        return InMemoryUserDetailsManager(user1 , user2)
    }

    @Bean
    fun passwordEncoder() : PasswordEncoder {
        return BCryptPasswordEncoder()
    }
}
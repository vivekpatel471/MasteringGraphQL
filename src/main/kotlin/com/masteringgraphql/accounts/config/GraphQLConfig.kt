package com.masteringgraphql.accounts.config

import graphql.scalars.ExtendedScalars
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.graphql.execution.RuntimeWiringConfigurer

@Configuration
class GraphQLConfig {
    @Bean
    fun dateTimeConfigurer(): RuntimeWiringConfigurer = RuntimeWiringConfigurer { wiringBuilder ->
        wiringBuilder.scalar(ExtendedScalars.DateTime)
    }

    @Bean
    fun countryCodeConfigurer(): RuntimeWiringConfigurer = RuntimeWiringConfigurer { wiringBuilder ->
        wiringBuilder.scalar(ExtendedScalars.CountryCode)
    }

    @Bean
    fun positiveFloatConfigurer(): RuntimeWiringConfigurer = RuntimeWiringConfigurer { wiringBuilder ->
        wiringBuilder.scalar(ExtendedScalars.PositiveFloat)
    }
}


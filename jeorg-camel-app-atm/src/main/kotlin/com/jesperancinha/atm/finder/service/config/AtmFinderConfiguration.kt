package com.jesperancinha.atm.finder.service.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.jesperancinha.atm.finder.camel.AtmService
import com.jesperancinha.atm.finder.service.AtmLocatorServiceImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer
import org.springframework.web.client.RestTemplate
import org.springframework.web.context.support.StandardServletEnvironment

/**
 * Created by joaofilipesabinoesperancinha on 09-08-16.
 */
@Configuration
@ComponentScan(basePackages = ["com.jesperancinha.atm.finder.service"])
@PropertySource(value = ["classpath:/application.properties"])
class AtmFinderConfiguration {
    @Autowired
    var atmLocatorService: AtmLocatorServiceImpl? = null

    @Autowired
    var atmService: AtmService? = null
    @Bean
    fun restTemplate(): RestTemplate {
        return RestTemplate()
    }

    @Bean
    fun objectMapper(): ObjectMapper {
        return ObjectMapper()
    }

    @Bean
    fun propertySourcesPlaceholderConfigurer(): PropertySourcesPlaceholderConfigurer {
        val propertySourcesPlaceholderConfigurer = PropertySourcesPlaceholderConfigurer()
        propertySourcesPlaceholderConfigurer.setEnvironment(StandardServletEnvironment())
        return propertySourcesPlaceholderConfigurer
    }
}
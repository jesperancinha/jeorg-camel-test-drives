package org.jesperancinha.atm.finder.service.config

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer
import org.springframework.web.client.RestTemplate
import org.springframework.web.context.support.StandardServletEnvironment

/**
 * Created by joaofilipesabinoesperancinha on 09-08-16.
 */
@Configuration
class AtmFinderConfiguration {

    @Bean
    fun restTemplate(): RestTemplate = RestTemplate()

    @Bean
    fun objectMapper(): ObjectMapper = ObjectMapper()

    @Bean
    fun propertySourcesPlaceholderConfigurer() = PropertySourcesPlaceholderConfigurer()
        .apply { setEnvironment(StandardServletEnvironment()) }
}
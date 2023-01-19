package com.jesperancinha.atm.finder.service.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jesperancinha.atm.finder.camel.AtmService;
import com.jesperancinha.atm.finder.service.AtmLocatorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.support.StandardServletEnvironment;

/**
 * Created by joaofilipesabinoesperancinha on 09-08-16.
 */
@Configuration
@ComponentScan(basePackages = {
    "com.jesperancinha.atm.finder.service"
})
@PropertySource(value = "classpath:/application.properties")
public class AtmFinderConfiguration {

    @Autowired
    AtmLocatorServiceImpl atmLocatorService;

    @Autowired
    AtmService atmService;

    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        final PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer = new PropertySourcesPlaceholderConfigurer();
        propertySourcesPlaceholderConfigurer.setEnvironment(new StandardServletEnvironment());
        return propertySourcesPlaceholderConfigurer;
    }

}

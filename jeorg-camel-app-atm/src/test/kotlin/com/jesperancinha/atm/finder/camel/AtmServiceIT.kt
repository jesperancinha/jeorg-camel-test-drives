package com.jesperancinha.atm.finder.camel

import io.kotest.matchers.shouldBe
import org.apache.camel.BeanInject
import org.apache.camel.builder.RouteBuilder
import org.apache.camel.language.bean.Bean
import org.apache.camel.spring.javaconfig.SingleRouteCamelConfiguration
import org.apache.camel.test.spring.CamelSpringDelegatingTestContextLoader
import org.apache.camel.test.spring.junit5.CamelSpringBootTest
import org.hamcrest.CoreMatchers
import org.jesperancinha.atm.finder.camel.AtmProvider
import org.jesperancinha.atm.finder.camel.AtmRestRouteBuilder
import org.jesperancinha.atm.finder.camel.AtmService
import org.jesperancinha.atm.finder.service.AtmLocatorServiceImpl
import org.jesperancinha.atm.finder.service.config.AtmFinderConfiguration
import org.junit.Test
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.Configuration
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.util.ReflectionTestUtils
import java.util.*

/**
 * Created by joaofilipesabinoesperancinha on 29-07-16.
 */
@CamelSpringBootTest
@EnableAutoConfiguration
@ContextConfiguration(
    classes = [AtmFinderConfiguration::class, AtmServiceIT.ContextConfig::class]
)
class AtmServiceIT @BeanInject constructor(
    private val atmService: AtmService,
) {

    @Throws(Exception::class)
    @Test
    fun `should uppercase atm providers`(){
            val atmLocatorService: AtmLocatorServiceImpl = ReflectionTestUtils.getField(
                atmService,
                ATM_LOCATOR_SERVICE
            ) as AtmLocatorServiceImpl
            ReflectionTestUtils.setField(atmLocatorService, ATM_ENDPOINT, HTTPS_WWW_ING_NL_API_LOCATOR_ATMS)
            val result: AtmProvider = atmService.getATMProvider(AMSTERDAM_UPPERCASE)
        result.atmMachines.size shouldBe 122
            result.atmMachines
                .forEach { atmMachine ->
                    atmMachine.address.city shouldBe AMSTERDAM_UPPERCASE
                }
        }

    @Throws(Exception::class)
    @Test
    fun `should lowercase atm providers`(){
            val atmLocatorService: AtmLocatorServiceImpl = ReflectionTestUtils.getField(
                atmService,
                ATM_LOCATOR_SERVICE
            ) as AtmLocatorServiceImpl
            ReflectionTestUtils.setField(atmLocatorService, ATM_ENDPOINT, HTTPS_WWW_ING_NL_API_LOCATOR_ATMS)
            val result: AtmProvider = atmService.getATMProvider(AMSTERDAM_LOWERCASE)
        result.atmMachines.size shouldBe 122
            result.atmMachines
                .forEach { atmMachine ->
                        atmMachine.address.city shouldBe AMSTERDAM_UPPERCASE
                }
        }

    @Configuration
    class ContextConfig : SingleRouteCamelConfiguration() {
        @Bean(ref = "")
        override fun route(): RouteBuilder {
            return AtmRestRouteBuilder()
        }
    }

    companion object {
        private const val HTTPS_WWW_ING_NL_API_LOCATOR_ATMS = "https://www.ing.nl/api/locator/atms/"
        private const val ATM_ENDPOINT = "atmEndpoint"
        private const val AMSTERDAM_UPPERCASE = "AMSTERDAM"
        private const val AMSTERDAM_LOWERCASE = "amsterdam"
        private const val ATM_LOCATOR_SERVICE = "atmLocatorService"
    }
}
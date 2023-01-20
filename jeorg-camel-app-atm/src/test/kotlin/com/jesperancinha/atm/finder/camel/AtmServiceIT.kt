package com.jesperancinha.atm.finder.camel

import io.kotest.matchers.shouldBe
import org.apache.camel.RoutesBuilder
import org.apache.camel.builder.RouteBuilder
import org.apache.camel.language.bean.Bean
import org.jesperancinha.atm.finder.camel.AtmProvider
import org.jesperancinha.atm.finder.camel.AtmService
import org.jesperancinha.atm.finder.service.AtmLocatorService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Configuration
import org.springframework.test.util.ReflectionTestUtils
import java.util.*


/**
 * Created by joaofilipesabinoesperancinha on 29-07-16.
 */
@SpringBootTest
class AtmServiceIT @Autowired constructor(
    private val atmService: AtmService,
    private val atmLocatorService: AtmLocatorService,
) {

    @Throws(Exception::class)
    @Test
    fun `should uppercase atm providers`() {
        val atmLocatorService: AtmLocatorService = ReflectionTestUtils.getField(
            atmService,
            ATM_LOCATOR_SERVICE
        ) as AtmLocatorService
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
    fun `should lowercase atm providers`() {
        val atmLocatorService: AtmLocatorService = ReflectionTestUtils.getField(
            atmService,
            ATM_LOCATOR_SERVICE
        ) as AtmLocatorService
        ReflectionTestUtils.setField(atmLocatorService, ATM_ENDPOINT, HTTPS_WWW_ING_NL_API_LOCATOR_ATMS)
        val result: AtmProvider = atmService.getATMProvider(AMSTERDAM_LOWERCASE)
        result.atmMachines.size shouldBe 122
        result.atmMachines
            .forEach { atmMachine ->
                atmMachine.address.city shouldBe AMSTERDAM_UPPERCASE
            }
    }

    @Configuration
    internal class ContextConfig {
        @Bean(ref = "route")
        fun route(): RoutesBuilder {
            return object : RouteBuilder() {
                @Throws(java.lang.Exception::class)
                override fun configure() {
                    from("direct:test").to("mock:test")
                }
            }
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
package com.jesperancinha.atm.finder.service

import com.jesperancinha.atm.finder.camel.AtmServiceIT
import io.kotest.matchers.shouldBe
import org.apache.camel.BeanInject
import org.apache.camel.RoutesBuilder
import org.apache.camel.builder.RouteBuilder
import org.apache.camel.language.bean.Bean
import org.apache.camel.test.spring.junit5.CamelSpringBootTest
import org.jesperancinha.atm.finder.service.AtmLocatorService
import org.jesperancinha.atm.finder.service.config.AtmFinderConfiguration
import org.jesperancinha.atm.finder.service.payload.response.ATMMachine
import org.junit.jupiter.api.Test
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.Configuration
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.util.ReflectionTestUtils
import java.util.*


/**
 * Created by joaofilipesabinoesperancinha on 28-07-16.
 */
@CamelSpringBootTest
@EnableAutoConfiguration
@ContextConfiguration(
    classes = [AtmFinderConfiguration::class, AtmLocatorServiceImpIT.ContextConfig::class]
)
class AtmLocatorServiceImpIT @BeanInject constructor(
    private val atmLocatorService: AtmLocatorService
) {


    @Throws(Exception::class)
    @Test
    fun `should find atm per city`() {
        ReflectionTestUtils.setField(atmLocatorService, ATM_ENDPOINT, HTTPS_WWW_ING_NL_API_LOCATOR_ATMS)
        val result: Array<ATMMachine> = atmLocatorService.getAtmPerCity(AMSTERDAM)
        result.size shouldBe 122
        Arrays.stream(result)
            .forEach { atmMachine ->
                atmMachine.address.city shouldBe AMSTERDAM
            }
    }

    @Configuration
    internal class ContextConfig {
        @Bean(ref="ref")
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
        private const val AMSTERDAM = "AMSTERDAM"
    }
}
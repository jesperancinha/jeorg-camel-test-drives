package com.jesperancinha.atm.finder.camel

import com.fasterxml.jackson.databind.ObjectMapper
import com.jesperancinha.atm.finder.dao.atms
import com.ninjasquad.springmockk.MockkBean
import io.kotest.matchers.shouldBe
import io.mockk.every
import org.apache.camel.RoutesBuilder
import org.apache.camel.builder.RouteBuilder
import org.apache.camel.language.bean.Bean
import org.apache.camel.test.spring.junit5.CamelSpringBootTest
import org.jesperancinha.atm.finder.camel.AtmProvider
import org.jesperancinha.atm.finder.camel.AtmService
import org.jesperancinha.atm.finder.service.AtmLocatorService
import org.jesperancinha.atm.finder.service.config.AtmFinderConfiguration
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Configuration
import org.springframework.http.ResponseEntity
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.util.ReflectionTestUtils
import org.springframework.test.util.ReflectionTestUtils.*
import org.springframework.web.client.RestTemplate
import java.util.*


/**
 * Created by joaofilipesabinoesperancinha on 29-07-16.
 */
@CamelSpringBootTest
@EnableAutoConfiguration
@SpringBootTest
@ContextConfiguration(
    classes = [AtmFinderConfiguration::class, AtmLocatorService::class, AtmService::class]
)
class AtmServiceIT {

    @Autowired
    lateinit var atmService: AtmService

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @MockkBean
    lateinit var restTemplate: RestTemplate

    @BeforeEach
    fun setUp(){
        every {  restTemplate.getForEntity(
            any<String>(),
            String::class.java)
        } returns  ResponseEntity.of(Optional.of("\n${objectMapper.writeValueAsString(atms)}"))
    }

    @Throws(Exception::class)
    @Test
    fun `should uppercase atm providers`() {
        val atmLocatorService: AtmLocatorService = getField(
            atmService,
            ATM_LOCATOR_SERVICE
        ) as AtmLocatorService
        setField(atmLocatorService, ATM_ENDPOINT, HTTPS_WWW_ING_NL_API_LOCATOR_ATMS)
        val result: AtmProvider = atmService.getATMProvider(GOUDA_UPPERCASE)
        result.atmMachines.size shouldBe 122
        result.atmMachines
            .forEach { atmMachine ->
                atmMachine.address.city shouldBe GOUDA_UPPERCASE
            }
    }

    @Throws(Exception::class)
    @Test
    fun `should lowercase atm providers`() {
        val atmLocatorService: AtmLocatorService = getField(
            atmService,
            ATM_LOCATOR_SERVICE
        ) as AtmLocatorService
        setField(atmLocatorService, ATM_ENDPOINT, HTTPS_WWW_ING_NL_API_LOCATOR_ATMS)
        val result: AtmProvider = atmService.getATMProvider(GOUDA_LOWERCASE)
        result.atmMachines.size shouldBe 122
        result.atmMachines
            .forEach { atmMachine ->
                atmMachine.address.city shouldBe GOUDA_UPPERCASE
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
        private const val GOUDA_UPPERCASE = "GOUDA"
        private const val GOUDA_LOWERCASE = "gouda"
        private const val ATM_LOCATOR_SERVICE = "atmLocatorService"
    }
}
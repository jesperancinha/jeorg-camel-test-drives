package org.jesperancinha.atm.finder.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.jesperancinha.atm.finder.dao.atms
import com.ninjasquad.springmockk.MockkBean
import io.kotest.matchers.shouldBe
import io.mockk.every
import org.apache.camel.test.spring.junit5.CamelSpringBootTest
import org.jesperancinha.atm.finder.service.config.AtmFinderConfiguration
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.ResponseEntity
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.util.ReflectionTestUtils.*
import org.springframework.web.client.RestTemplate
import java.util.*


/**
 * Created by joaofilipesabinoesperancinha on 28-07-16.
 */
@CamelSpringBootTest
@EnableAutoConfiguration
@SpringBootTest
@ContextConfiguration(
    classes = [AtmFinderConfiguration::class, AtmLocatorService::class]
)
class AtmLocatorServiceImpIT {

    @Autowired
    lateinit var atmLocatorService: AtmLocatorService

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @MockkBean
    lateinit var restTemplate: RestTemplate

    @Throws(Exception::class)
    @Test
    fun `should find atm per city`() {
        setField(atmLocatorService, ATM_ENDPOINT, HTTPS_WWW_ING_NL_API_LOCATOR_ATMS)
        every {  restTemplate.getForEntity(
            any<String>(),
            String::class.java)
        } returns  ResponseEntity.of(Optional.of("\n${objectMapper.writeValueAsString(atms)}"))
        val result = atmLocatorService.getAtmPerCity(GOUDA)
        result.size shouldBe 122
        Arrays.stream(result)
            .forEach { atmMachine ->
                atmMachine.address.city shouldBe GOUDA
            }
    }

    companion object {
        private const val HTTPS_WWW_ING_NL_API_LOCATOR_ATMS = "https://www.ing.nl/api/locator/atms/"
        private const val ATM_ENDPOINT = "atmEndpoint"
        private const val GOUDA = "GOUDA"
    }
}
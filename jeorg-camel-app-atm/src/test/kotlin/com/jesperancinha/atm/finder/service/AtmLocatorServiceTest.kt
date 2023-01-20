package com.jesperancinha.atm.finder.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.google.common.io.CharStreams
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import org.jesperancinha.atm.finder.service.AtmLocatorService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.http.ResponseEntity
import org.springframework.test.util.ReflectionTestUtils
import org.springframework.web.client.RestTemplate
import java.io.InputStreamReader
import java.util.*

/**
 * Created by joaofilipesabinoesperancinha on 29-07-16.
 */
@ExtendWith(MockKExtension::class)
class AtmLocatorServiceTest {
    @InjectMockKs
    lateinit var atmLocatorService: AtmLocatorService

    @MockK
    lateinit var restTemplate: RestTemplate
    private val mapper: ObjectMapper by lazy { ObjectMapper() }

    @BeforeEach
    fun setUp() {
        ReflectionTestUtils.setField(atmLocatorService, MAPPER, mapper)
    }

    @Throws(Exception::class)
    @Test
    fun `should clean file`() {
        val resourceClean = javaClass
            .getResourceAsStream(MOCK_CLEAN_RESPONSE1_JSON).shouldNotBeNull()
        val resourceBody: String = CharStreams.toString(InputStreamReader(resourceClean))
        val stringResponseEntity: ResponseEntity<String> = mockk()
        every { restTemplate.getForEntity(any<String>(), String::class.java) } returns stringResponseEntity
        every { stringResponseEntity.body } returns resourceBody
        val result = atmLocatorService.getAtmPerCity(AMSTERDAM)
        result.size shouldBe 122
        result.forEach { atmMachine -> atmMachine.address.city shouldBe AMSTERDAM }
    }

    @Throws(Exception::class)
    @Test
    fun `should process ATM per city corrupted file`() {
        val resourceClean = javaClass
            .getResourceAsStream(MOCK_GARBAGE_RESPONSE1_JSON).shouldNotBeNull()
        val resourceBody: String = CharStreams.toString(InputStreamReader(resourceClean))
        val stringResponseEntity: ResponseEntity<String> = mockk()
        every { restTemplate.getForEntity(any<String>(), String::class.java) } returns stringResponseEntity
        every { stringResponseEntity.body } returns resourceBody
        val result = atmLocatorService.getAtmPerCity(AMSTERDAM)
        result.size shouldBe 122
        result.forEach { atmMachine -> atmMachine.address.city shouldBe AMSTERDAM }
    }

    companion object {
        private const val MAPPER = "mapper"
        private const val MOCK_CLEAN_RESPONSE1_JSON = "/mockCleanResponse1.json"
        private const val AMSTERDAM = "AMSTERDAM"
        private const val MOCK_GARBAGE_RESPONSE1_JSON = "/mockGarbageResponse1.json"
    }
}
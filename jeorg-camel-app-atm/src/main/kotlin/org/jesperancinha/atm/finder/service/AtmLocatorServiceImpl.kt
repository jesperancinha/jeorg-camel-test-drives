package org.jesperancinha.atm.finder.service

import com.fasterxml.jackson.databind.ObjectMapper
import lombok.Setter
import org.apache.camel.BeanInject
import org.jesperancinha.atm.finder.service.payload.response.ATMMachine
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import java.io.IOException
import java.util.*

/**
 * Created by joaofilipesabinoesperancinha on 28-07-16.
 */
@Setter
@Service(value = "atmLocatorService")
class AtmLocatorServiceImpl {
    @BeanInject
    lateinit var restTemplate: RestTemplate

    @BeanInject
    lateinit var  mapper: ObjectMapper

    @Value("\${atm.endpoint}")
    lateinit var  atmEndpoint: String

    @Throws(IOException::class)
    fun getAtmPerCity(city: String): Array<ATMMachine> {
        val response = restTemplate.getForEntity(
            atmEndpoint,
            String::class.java
        )
        var body = requireNotNull(response.body)
        if (!body.startsWith("{") && !body.startsWith("[")) {
            body = body.substring(body.indexOf('\n') + 1)
        }
        val results = mapper.readValue(body, Array<ATMMachine>::class.java)
        return results
            .filter { atmMachine: ATMMachine -> atmMachine.address.city == city }
            .toTypedArray()
    }
}
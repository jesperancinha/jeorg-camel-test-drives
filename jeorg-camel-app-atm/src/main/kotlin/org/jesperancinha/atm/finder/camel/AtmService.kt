package org.jesperancinha.atm.finder.camel

import org.jesperancinha.atm.finder.service.AtmLocatorServiceImpl
import org.apache.camel.BeanInject
import org.springframework.stereotype.Component
import java.io.IOException
import java.util.*

@Component
class AtmService {
    @BeanInject
    lateinit var atmLocatorService: AtmLocatorServiceImpl
    @Throws(IOException::class)
    fun getATMProvider(city: String): AtmProvider {
        return AtmProvider(
            atmMachines=atmLocatorService.getAtmPerCity(city.uppercase(Locale.getDefault())),
            city = city)
    }
}
package com.jesperancinha.atm.finder.camel

import com.jesperancinha.atm.finder.service.AtmLocatorServiceImpl
import org.apache.camel.BeanInject
import org.springframework.stereotype.Component
import java.io.IOException
import java.util.*

@Component
class AtmService {
    @BeanInject
    private val atmLocatorService: AtmLocatorServiceImpl? = null
    @Throws(IOException::class)
    fun getATMProvider(city: String): AtmProvider {
        return AtmProvider.builder().atmMachines(atmLocatorService!!.getAtmPerCity(city.uppercase(Locale.getDefault())))
            .city(city).build()
    }
}
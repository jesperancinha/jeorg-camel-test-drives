package org.jesperancinha.atm.finder.camel

import org.jesperancinha.atm.finder.service.AtmLocatorService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.io.IOException
import java.util.*

@Service
class AtmService(
    @Autowired
    val atmLocatorService: AtmLocatorService
) {

    @Throws(IOException::class)
    fun getATMProvider(city: String): AtmProvider {
        return AtmProvider(
            atmMachines = atmLocatorService.getAtmPerCity(city.uppercase(Locale.getDefault())),
            city = city
        )
    }
}
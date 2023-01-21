package com.jesperancinha.atm.finder.dao

import org.jesperancinha.atm.finder.service.payload.response.ATMAddress
import org.jesperancinha.atm.finder.service.payload.response.ATMGeoLocation
import org.jesperancinha.atm.finder.service.payload.response.ATMMachine

val atm = ATMMachine(
    address = ATMAddress(
        street = "Kaas Plein",
        housenumber = "616",
        postalcode = "2801XX",
        city = "GOUDA",
        geoLocation = ATMGeoLocation(
            52.0174611, 4.7040779
        )
    ),
    distance = 0,
    type = "We are all the same"
)
val atms: Array<ATMMachine> = (1..122).map { atm }.toTypedArray()
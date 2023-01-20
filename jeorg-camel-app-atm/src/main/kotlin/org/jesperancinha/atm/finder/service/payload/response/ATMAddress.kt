package org.jesperancinha.atm.finder.service.payload.response

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Created by joaofilipesabinoesperancinha on 28-07-16.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
class ATMAddress(
    @JsonProperty("street")
    val street: String,

    @JsonProperty("housenumber")
    val housenumber: String,

    @JsonProperty("postalcode")
    val postalcode: String,

    @JsonProperty("city")
    val city: String,

    @JsonProperty("geoLocation")
    val geoLocation: ATMGeoLocation
)
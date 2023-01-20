package org.jesperancinha.atm.finder.service.payload.response

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Created by joaofilipesabinoesperancinha on 28-07-16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
class ATMGeoLocation {
    @JsonProperty("lat")
    private val lat: Double? = null

    @JsonProperty("lng")
    private val lng: Double? = null
}
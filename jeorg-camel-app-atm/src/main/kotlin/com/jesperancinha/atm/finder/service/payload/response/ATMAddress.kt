package com.jesperancinha.atm.finder.service.payload.response

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import lombok.Getter
import lombok.Setter

/**
 * Created by joaofilipesabinoesperancinha on 28-07-16.
 */
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
class ATMAddress {
    @JsonProperty("street")
    private val street: String? = null

    @JsonProperty("housenumber")
    private val housenumber: String? = null

    @JsonProperty("postalcode")
    private val postalcode: String? = null

    @JsonProperty("city")
    private val city: String? = null

    @JsonProperty("geoLocation")
    private val geoLocation: ATMGeoLocation? = null
}
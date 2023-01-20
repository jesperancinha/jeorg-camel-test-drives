package org.jesperancinha.atm.finder.service.payload.response

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
class ATMGeoLocation {
    @JsonProperty("lat")
    private val lat: Double? = null

    @JsonProperty("lng")
    private val lng: Double? = null
}
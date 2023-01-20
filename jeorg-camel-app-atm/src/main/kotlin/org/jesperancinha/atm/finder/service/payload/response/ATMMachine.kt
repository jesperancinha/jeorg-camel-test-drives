package org.jesperancinha.atm.finder.service.payload.response

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Created by joaofilipesabinoesperancinha on 28-07-16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
data class ATMMachine(
    @JsonProperty("address")
    val address: ATMAddress,

    @JsonProperty("distance")
    val distance: Int,

    @JsonProperty("type")
    val type: String
)
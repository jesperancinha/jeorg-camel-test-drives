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
class ATMMachine {
    @JsonProperty("address")
    private val address: ATMAddress? = null

    @JsonProperty("distance")
    private val distance: Int? = null

    @JsonProperty("type")
    private val type: String? = null
}
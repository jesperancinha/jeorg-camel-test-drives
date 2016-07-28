package com.jesperancinha.atm.finder.service.payload.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by joaofilipesabinoesperancinha on 28-07-16.
 */
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ATMAddress {

    @JsonProperty("street")
    private String street;

    @JsonProperty("housenumber")
    private String housenumber;

    @JsonProperty("postalcode")
    private String postalcode;

    @JsonProperty("city")
    private String city;

    @JsonProperty("geoLocation")
    private ATMGeoLocation geoLocation;
}

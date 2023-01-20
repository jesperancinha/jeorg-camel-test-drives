package org.jesperancinha.atm.finder.camel

import org.apache.camel.model.rest.RestBindingMode
import org.apache.camel.spring.SpringRouteBuilder
import org.springframework.stereotype.Component

@Component
class AtmRestRouteBuilder : SpringRouteBuilder() {
    @Throws(Exception::class)
    override fun configure() {
        restConfiguration().component("servlet").bindingMode(RestBindingMode.json)
            .dataFormatProperty("prettyPrint", "true")
            .contextPath("jesperancinha-atm-finder/rest").port(8080)
        rest("/provider").description("AtmProvider rest service")
            .consumes("application/json").produces("application/json")["/{city}/atms"].description("Find atm by city")
            .outType(AtmProvider::class.java)
            .to("bean:atmService?method=getATMProvider(\${header.city})")
    }
}
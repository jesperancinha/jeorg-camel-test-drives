/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jesperancinha.atm.finder.camel

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
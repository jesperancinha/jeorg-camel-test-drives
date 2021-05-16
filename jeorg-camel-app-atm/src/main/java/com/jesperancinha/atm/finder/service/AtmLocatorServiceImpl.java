package com.jesperancinha.atm.finder.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jesperancinha.atm.finder.service.payload.response.ATMMachine;
import lombok.Setter;
import org.apache.camel.BeanInject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Arrays;

/**
 * Created by joaofilipesabinoesperancinha on 28-07-16.
 */

@Setter
@Service(value = "atmLocatorService")
public class AtmLocatorServiceImpl implements AtmLocatorService {

    @BeanInject
    private RestTemplate restTemplate;

    @BeanInject
    private ObjectMapper mapper;

    @Value("${atm.endpoint}")
    private String atmEndpoint;

    public ATMMachine[] getAtmPerCity(String city) throws IOException {
        ResponseEntity<String> response = restTemplate
                .getForEntity(
                        atmEndpoint,
                        String.class);

        String body = response.getBody();
        if(!body.startsWith("{") && !body.startsWith("[")) {
            body = body.substring(body.indexOf('\n') + 1);
        }

        ATMMachine[] results = mapper.readValue(body, ATMMachine[].class);

        return Arrays
                .stream(results)
                .filter(atmMachine -> atmMachine.getAddress().getCity().equals(city))
                .toArray(ATMMachine[]::new);
    }
}

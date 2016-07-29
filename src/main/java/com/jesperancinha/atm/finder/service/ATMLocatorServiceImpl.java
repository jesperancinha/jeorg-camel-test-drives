package com.jesperancinha.atm.finder.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jesperancinha.atm.finder.service.payload.response.ATMMachine;
import lombok.Setter;
import org.apache.camel.BeanInject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Arrays;

/**
 * Created by joaofilipesabinoesperancinha on 28-07-16.
 */

@Setter
public class ATMLocatorServiceImpl implements ATMLocatorService {

    @BeanInject
    private RestTemplate restTemplate;

    @BeanInject
    private ObjectMapper mapper;

    private String atmEndpoint;

    public ATMMachine[] getAtmPerCity(String city) throws IOException {
        ResponseEntity<String> response = restTemplate
                .getForEntity(
                        atmEndpoint,
                        String.class);

        String body = response.getBody();
        body = body.substring(body.indexOf('\n') + 1);

        ATMMachine[] results = mapper.readValue(body, ATMMachine[].class);

        return Arrays
                .stream(results)
                .filter(atmMachine -> atmMachine.getAddress().getCity().equals(city))
                .toArray(ATMMachine[]::new);
    }
}

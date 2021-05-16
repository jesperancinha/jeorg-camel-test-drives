package com.jesperancinha.atm.finder.camel;

import com.jesperancinha.atm.finder.service.AtmLocatorService;
import org.apache.camel.BeanInject;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AtmService {

    @BeanInject
    private AtmLocatorService atmLocatorService;

    public AtmProvider getATMProvider(String city) throws IOException {
        return AtmProvider.builder().atmMachines(atmLocatorService.getAtmPerCity(city.toUpperCase()))
                .city(city).build();
    }
}
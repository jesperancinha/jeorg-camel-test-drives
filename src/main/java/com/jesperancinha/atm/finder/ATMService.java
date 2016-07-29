package com.jesperancinha.atm.finder;

import com.jesperancinha.atm.finder.service.ATMLocatorService;
import org.apache.camel.BeanInject;

import java.io.IOException;

public class ATMService {

    @BeanInject
    private ATMLocatorService atmLocatorService;

    public ATMProvider getATMProvider(String city) throws IOException {
        return ATMProvider.builder().atmMachines(atmLocatorService.getAtmPerCity(city.toUpperCase()))
                .city(city).build();
    }
}
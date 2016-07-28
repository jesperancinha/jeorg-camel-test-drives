package com.jesperancinha.atm.finder;

import com.jesperancinha.atm.finder.service.ATMLocatorService;
import lombok.NoArgsConstructor;
import org.apache.camel.BeanInject;

import java.io.IOException;

@NoArgsConstructor
public class ATMService {

    @BeanInject
    private ATMLocatorService atmLocatorService;

    public ATMProvider getATMProvider(String city) throws IOException {
//        return ATMProvider.builder().city(city).build();
        return ATMProvider.builder().atmMachines(atmLocatorService.getAtmPerCity(city))
                .city(city).build();
    }
}
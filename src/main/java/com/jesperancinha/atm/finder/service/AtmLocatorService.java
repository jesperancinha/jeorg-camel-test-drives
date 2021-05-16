package com.jesperancinha.atm.finder.service;


import com.jesperancinha.atm.finder.service.payload.response.ATMMachine;

import java.io.IOException;

/**
 * Created by joaofilipesabinoesperancinha on 28-07-16.
 */
public interface AtmLocatorService {

    ATMMachine[] getAtmPerCity(String City) throws IOException;
}

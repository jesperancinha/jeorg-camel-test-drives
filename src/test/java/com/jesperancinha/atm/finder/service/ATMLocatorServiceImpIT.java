package com.jesperancinha.atm.finder.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jesperancinha.atm.finder.ATMRestRouteBuilder;
import com.jesperancinha.atm.finder.service.payload.response.ATMMachine;
import org.apache.camel.BeanInject;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.language.Bean;
import org.apache.camel.spring.javaconfig.SingleRouteCamelConfiguration;
import org.apache.camel.test.spring.CamelSpringDelegatingTestContextLoader;
import org.apache.camel.test.spring.CamelSpringJUnit4ClassRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

import static junit.framework.TestCase.assertEquals;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by joaofilipesabinoesperancinha on 28-07-16.
 */
@RunWith(CamelSpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ATMLocatorServiceImpIT.ContextConfig.class,
        ATMLocatorServiceImpl.class,
        RestTemplate.class,
        ObjectMapper.class
},
        loader = CamelSpringDelegatingTestContextLoader.class)
public class ATMLocatorServiceImpIT extends AbstractJUnit4SpringContextTests {

    private static final String HTTPS_WWW_ING_NL_API_LOCATOR_ATMS = "https://www.ing.nl/api/locator/atms/";
    private static final String ATM_ENDPOINT = "atmEndpoint";
    private static final String AMSTERDAM = "AMSTERDAM";

    @BeanInject
    ATMLocatorService atmLocatorService;

    @Test
    public void getAtmPerCity() throws Exception {
        ReflectionTestUtils.setField(atmLocatorService, ATM_ENDPOINT, HTTPS_WWW_ING_NL_API_LOCATOR_ATMS);
        ATMMachine[] result = atmLocatorService.getAtmPerCity(AMSTERDAM);

        assertEquals(122, result.length);
        Arrays.stream(result)
                .forEach(atmMachine -> assertThat(atmMachine.getAddress().getCity(), equalTo(AMSTERDAM)));
    }

    @Configuration
    public static class ContextConfig extends SingleRouteCamelConfiguration {

        @Bean(ref = "")
        @Override
        public RouteBuilder route() {
            return new ATMRestRouteBuilder();
        }
    }
}
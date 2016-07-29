package com.jesperancinha.atm.finder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jesperancinha.atm.finder.service.ATMLocatorService;
import com.jesperancinha.atm.finder.service.ATMLocatorServiceImpIT;
import com.jesperancinha.atm.finder.service.ATMLocatorServiceImpl;
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
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

import static junit.framework.TestCase.assertEquals;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by joaofilipesabinoesperancinha on 29-07-16.
 */
@RunWith(CamelSpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {
        ATMLocatorServiceImpIT.ContextConfig.class,
        ATMService.class,
        ATMLocatorServiceImpl.class,
        RestTemplate.class,
        ObjectMapper.class
},
        loader = CamelSpringDelegatingTestContextLoader.class)
public class ATMServiceIT {

    private static final String HTTPS_WWW_ING_NL_API_LOCATOR_ATMS = "https://www.ing.nl/api/locator/atms/";
    private static final String ATM_ENDPOINT = "atmEndpoint";
    private static final String AMSTERDAM_UPPERCASE = "AMSTERDAM";
    private static final String AMSTERDAM_LOWERCASE = "amsterdam";
    private static final String ATM_LOCATOR_SERVICE = "atmLocatorService";

    @BeanInject
    ATMService atmService;

    @Test
    public void getATMProvider_Uppercase() throws Exception {
        ATMLocatorService atmLocatorService = (ATMLocatorService) ReflectionTestUtils.getField(
                atmService,
                ATM_LOCATOR_SERVICE
        );
        ReflectionTestUtils.setField(atmLocatorService, ATM_ENDPOINT, HTTPS_WWW_ING_NL_API_LOCATOR_ATMS);
        ATMProvider result = atmService.getATMProvider(AMSTERDAM_UPPERCASE);

        assertEquals(122, result.getAtmMachines().length);
        Arrays.stream(result.getAtmMachines())
                .forEach(atmMachine -> assertThat(atmMachine.getAddress().getCity(), equalTo(AMSTERDAM_UPPERCASE)));
    }

    @Test
    public void getATMProvider_Lowercase() throws Exception {
        ATMLocatorService atmLocatorService = (ATMLocatorService) ReflectionTestUtils.getField(
                atmService,
                ATM_LOCATOR_SERVICE
        );
        ReflectionTestUtils.setField(atmLocatorService, ATM_ENDPOINT, HTTPS_WWW_ING_NL_API_LOCATOR_ATMS);
        ATMProvider result = atmService.getATMProvider(AMSTERDAM_LOWERCASE);

        assertEquals(122, result.getAtmMachines().length);
        Arrays.stream(result.getAtmMachines())
                .forEach(atmMachine -> assertThat(atmMachine.getAddress().getCity(), equalTo(AMSTERDAM_UPPERCASE)));
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
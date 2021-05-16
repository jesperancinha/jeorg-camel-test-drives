package com.jesperancinha.atm.finder.camel;

import com.jesperancinha.atm.finder.service.AtmLocatorService;
import com.jesperancinha.atm.finder.service.config.AtmFinderConfiguration;
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

import java.util.Arrays;

import static junit.framework.TestCase.assertEquals;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by joaofilipesabinoesperancinha on 29-07-16.
 */
@RunWith(CamelSpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {
        AtmFinderConfiguration.class,
        AtmServiceIT.ContextConfig.class
},
        loader = CamelSpringDelegatingTestContextLoader.class)
public class AtmServiceIT extends AbstractJUnit4SpringContextTests {

    private static final String HTTPS_WWW_ING_NL_API_LOCATOR_ATMS = "https://www.ing.nl/api/locator/atms/";
    private static final String ATM_ENDPOINT = "atmEndpoint";
    private static final String AMSTERDAM_UPPERCASE = "AMSTERDAM";
    private static final String AMSTERDAM_LOWERCASE = "amsterdam";
    private static final String ATM_LOCATOR_SERVICE = "atmLocatorService";

    @BeanInject
    AtmService atmService;

    @BeanInject
    AtmLocatorService atmLocatorService;

    @Test
    public void getATMProvider_Uppercase() throws Exception {
        AtmLocatorService atmLocatorService = (AtmLocatorService) ReflectionTestUtils.getField(
                atmService,
                ATM_LOCATOR_SERVICE
        );
        ReflectionTestUtils.setField(atmLocatorService, ATM_ENDPOINT, HTTPS_WWW_ING_NL_API_LOCATOR_ATMS);
        AtmProvider result = atmService.getATMProvider(AMSTERDAM_UPPERCASE);

        assertEquals(122, result.getAtmMachines().length);
        Arrays.stream(result.getAtmMachines())
                .forEach(atmMachine -> assertThat(atmMachine.getAddress().getCity(), equalTo(AMSTERDAM_UPPERCASE)));
    }

    @Test
    public void getATMProvider_Lowercase() throws Exception {
        AtmLocatorService atmLocatorService = (AtmLocatorService) ReflectionTestUtils.getField(
                atmService,
                ATM_LOCATOR_SERVICE
        );
        ReflectionTestUtils.setField(atmLocatorService, ATM_ENDPOINT, HTTPS_WWW_ING_NL_API_LOCATOR_ATMS);
        AtmProvider result = atmService.getATMProvider(AMSTERDAM_LOWERCASE);

        assertEquals(122, result.getAtmMachines().length);
        Arrays.stream(result.getAtmMachines())
                .forEach(atmMachine -> assertThat(atmMachine.getAddress().getCity(), equalTo(AMSTERDAM_UPPERCASE)));
    }

    @Configuration
    public static class ContextConfig extends SingleRouteCamelConfiguration {

        @Bean(ref = "")
        @Override
        public RouteBuilder route() {
            return new AtmRestRouteBuilder();
        }
    }
}
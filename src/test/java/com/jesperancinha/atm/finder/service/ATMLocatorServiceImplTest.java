package com.jesperancinha.atm.finder.service;

import com.jesperancinha.atm.finder.ATMRestRouteBuilder;
import com.jesperancinha.atm.finder.service.payload.response.ATMMachine;
import org.apache.camel.BeanInject;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.language.Bean;
import org.apache.camel.spring.javaconfig.SingleRouteCamelConfiguration;
import org.apache.camel.test.spring.CamelSpringDelegatingTestContextLoader;
import org.apache.camel.test.spring.CamelSpringJUnit4ClassRunner;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import java.util.Arrays;

import static junit.framework.TestCase.assertEquals;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by joaofilipesabinoesperancinha on 28-07-16.
 */
@RunWith(CamelSpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ATMLocatorServiceImplTest.ContextConfig.class, ATMLocatorServiceImpl.class}, loader = CamelSpringDelegatingTestContextLoader.class)
public class ATMLocatorServiceImplTest extends AbstractJUnit4SpringContextTests {

    @BeanInject
    ATMLocatorService atmLocatorService;

    @Test
    @Ignore
    public void getAtmPerCity() throws Exception {
        ((ATMLocatorServiceImpl) atmLocatorService).setAtmEndpoint("https://www.ing.nl/api/locator/atms/");
        ATMMachine[] result = atmLocatorService.getAtmPerCity("AMSTERDAM");
        assertEquals(122, result.length);

        Arrays.stream(result)
                .forEach(atmMachine -> assertThat(atmMachine.getAddress().getCity(), equalTo("AMSTERDAM")));
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
package com.jesperancinha.atm.finder.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.CharStreams;
import com.jesperancinha.atm.finder.service.payload.response.ATMMachine;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;

import static junit.framework.TestCase.assertEquals;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by joaofilipesabinoesperancinha on 29-07-16.
 */
@RunWith(MockitoJUnitRunner.class)
public class ATMLocatorServiceImplTest {

    private static final String MAPPER = "mapper";
    private static final String MOCK_CLEAN_RESPONSE1_JSON = "/mockCleanResponse1.json";
    private static final String AMSTERDAM = "AMSTERDAM";
    private static final String MOCK_GARBAGE_RESPONSE1_JSON = "/mockGarbageResponse1.json";

    @InjectMocks
    ATMLocatorServiceImpl atmLocatorService;

    @Mock
    RestTemplate restTemplate;

    ObjectMapper mapper;

    @Before
    public void setUp()
    {
        mapper = new ObjectMapper();
        ReflectionTestUtils.setField(atmLocatorService, MAPPER,mapper);
    }

    @Test
    public void getAtmPerCity_CleanFile() throws Exception {
        final InputStream resourceClean = getClass().getResourceAsStream(MOCK_CLEAN_RESPONSE1_JSON);
        final String resourceBody = CharStreams.toString(new InputStreamReader(resourceClean));

        final ResponseEntity<String>  stringResponseEntity = mock(ResponseEntity.class);

        when(restTemplate.getForEntity(any(String.class), eq(String.class))).thenReturn(stringResponseEntity);
        when(stringResponseEntity.getBody()).thenReturn(resourceBody);

        ATMMachine[] result = atmLocatorService.getAtmPerCity(AMSTERDAM);

        assertEquals(122, result.length);
        Arrays.stream(result)
                .forEach(atmMachine -> assertThat(atmMachine.getAddress().getCity(), equalTo(AMSTERDAM)));
    }

    @Test
    public void getAtmPerCity_CorruptedFile() throws Exception {
        final InputStream resourceClean = getClass().getResourceAsStream(MOCK_GARBAGE_RESPONSE1_JSON);
        final String resourceBody = CharStreams.toString(new InputStreamReader(resourceClean));

        final ResponseEntity<String>  stringResponseEntity = mock(ResponseEntity.class);

        when(restTemplate.getForEntity(any(String.class), eq(String.class))).thenReturn(stringResponseEntity);
        when(stringResponseEntity.getBody()).thenReturn(resourceBody);

        ATMMachine[] result = atmLocatorService.getAtmPerCity(AMSTERDAM);

        assertEquals(122, result.length);
        Arrays.stream(result)
                .forEach(atmMachine -> assertThat(atmMachine.getAddress().getCity(), equalTo(AMSTERDAM)));
    }

}
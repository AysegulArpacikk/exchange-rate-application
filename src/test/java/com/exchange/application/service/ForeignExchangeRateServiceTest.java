package com.exchange.application.service;

import com.exchange.application.dto.RateResponseDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
class ForeignExchangeRateServiceTest {

    @InjectMocks
    private ForeignExchangeRateService foreignExchangeRateService;

    @Mock
    private RestTemplate restTemplate;

    @Test
    public void shouldFetchRateResponse() {
        String access_key = "136c0830a0ce27f2e02c7e333178d2b3";
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://data.fixer.io/api/latest")
                .queryParam("access_key", access_key);

        foreignExchangeRateService.fetchRateResponse();

        verify(restTemplate).getForObject(builder.toUriString(), RateResponseDto.class);
    }
}

package com.exchange.application.service;

import com.exchange.application.cache.CacheNames;
import com.exchange.application.dto.RateResponseDto;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class ForeignExchangeRateService {

    private static final String API_ACCESS_KEY = "136c0830a0ce27f2e02c7e333178d2b3";

    private final RestTemplate restTemplate;

    public ForeignExchangeRateService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Cacheable(value = CacheNames.ONE_DAY_IN_MEMORY, key = "'latestRates'")
    public RateResponseDto rateResponse() {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://data.fixer.io/api/latest")
                .queryParam("access_key", API_ACCESS_KEY);

        return restTemplate.getForObject(builder.toUriString(), RateResponseDto.class);
    }
}

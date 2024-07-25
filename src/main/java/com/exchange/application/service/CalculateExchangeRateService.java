package com.exchange.application.service;

import com.exchange.application.dto.RateResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class CalculateExchangeRateService {

    @Autowired
    private ExchangeRateService exchangeRateService;

    public BigDecimal calculateExchangeRate(String sourceCurrencyCode, String targetCurrencyCode) {
        RateResponseDto rateResponseDto = exchangeRateService.rateResponse();
        return rateResponseDto.getRates().get(targetCurrencyCode)
                .divide(rateResponseDto.getRates().get(sourceCurrencyCode), 3, RoundingMode.CEILING);
    }

}

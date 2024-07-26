package com.exchange.application.service;

import com.exchange.application.dto.RateResponseDto;
import com.exchange.application.exception.ExceptionResponse;
import com.exchange.application.exception.SourceCurrencyCodeNotFoundException;
import com.exchange.application.exception.TargetCurrencyCodeNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class CalculateExchangeRateService {

    @Autowired
    private ForeignExchangeRateService foreignExchangeRateService;

    public BigDecimal calculateExchangeRate(String sourceCurrencyCode, String targetCurrencyCode) {
        RateResponseDto rateResponseDto = foreignExchangeRateService.rateResponse();
        if (!rateResponseDto.getRates().containsKey(sourceCurrencyCode)) {
            throw new SourceCurrencyCodeNotFoundException(ExceptionResponse.SOURCE_CURRENCY_CODE_NOT_FOUND);
        } if (!rateResponseDto.getRates().containsKey(targetCurrencyCode)) {
            throw new TargetCurrencyCodeNotFoundException(ExceptionResponse.TARGET_CURRENCY_CODE_NOT_FOUND);
        }

        return rateResponseDto.getRates().get(targetCurrencyCode)
                .divide(rateResponseDto.getRates().get(sourceCurrencyCode), 3, RoundingMode.CEILING);
    }

}

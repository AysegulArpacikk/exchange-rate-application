package com.exchange.application.service;

import com.exchange.application.dto.RateResponseDto;
import com.exchange.application.exception.SourceCurrencyCodeNotFoundException;
import com.exchange.application.exception.TargetCurrencyCodeNotFoundException;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
public class CalculateExchangeRateServiceTest {

    @InjectMocks
    private CalculateExchangeRateService calculateExchangeRateService;

    @Mock
    private ForeignExchangeRateService foreignExchangeRateService;

    @Test
    public void shouldCalculateExchangeRate() {
        String sourceCurrencyCode = "TRY";
        String targetCurrencyCode = "AZN";
        Map<String, BigDecimal> rates = new HashMap<>();
        rates.put("TRY", new BigDecimal(5));
        rates.put("USD", new BigDecimal(3));
        rates.put("AZN", new BigDecimal(4));

        RateResponseDto rateResponseDto = new RateResponseDto();
        rateResponseDto.setSuccess(true);
        rateResponseDto.setBase("EUR");
        rateResponseDto.setRates(rates);

        when(foreignExchangeRateService.fetchRateResponse()).thenReturn(rateResponseDto);

        BigDecimal result = calculateExchangeRateService.calculateExchangeRate(sourceCurrencyCode, targetCurrencyCode);

        assertEquals(new BigDecimal("0.800"), result);
    }

    @Test
    public void shouldThrowExceptionForCalculateExchangeRateWhenSourceCurrencyCodeIsNotFound() {
        String sourceCurrencyCode = "TRYY";
        String targetCurrencyCode = "AZN";
        Map<String, BigDecimal> rates = new HashMap<>();
        rates.put("TRY", new BigDecimal(5));
        rates.put("USD", new BigDecimal(3));
        rates.put("AZN", new BigDecimal(4));

        RateResponseDto rateResponseDto = new RateResponseDto();
        rateResponseDto.setSuccess(true);
        rateResponseDto.setBase("EUR");
        rateResponseDto.setRates(rates);

        when(foreignExchangeRateService.fetchRateResponse()).thenReturn(rateResponseDto);

        Exception exception = assertThrows(SourceCurrencyCodeNotFoundException.class, () -> {
            calculateExchangeRateService.calculateExchangeRate(sourceCurrencyCode, targetCurrencyCode);
        });

        String expectedMessage = "Source currency code not found!";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void shouldThrowExceptionForCalculateExchangeRateWhenTargetCurrencyCodeIsNotFound() {
        String sourceCurrencyCode = "TRY";
        String targetCurrencyCode = "azn";
        Map<String, BigDecimal> rates = new HashMap<>();
        rates.put("TRY", new BigDecimal(5));
        rates.put("USD", new BigDecimal(3));
        rates.put("AZN", new BigDecimal(4));

        RateResponseDto rateResponseDto = new RateResponseDto();
        rateResponseDto.setSuccess(true);
        rateResponseDto.setBase("EUR");
        rateResponseDto.setRates(rates);

        when(foreignExchangeRateService.fetchRateResponse()).thenReturn(rateResponseDto);

        Exception exception = assertThrows(TargetCurrencyCodeNotFoundException.class, () -> {
            calculateExchangeRateService.calculateExchangeRate(sourceCurrencyCode, targetCurrencyCode);
        });

        String expectedMessage = "Target currency code not found!";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}

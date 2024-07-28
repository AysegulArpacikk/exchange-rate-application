package com.exchange.application.controller;

import com.exchange.application.converter.ConversionConverter;
import com.exchange.application.dto.ConversionRequestDto;
import com.exchange.application.dto.ConversionResponseDto;
import com.exchange.application.dto.RateResponseDto;
import com.exchange.application.exception.ExceptionResponse;
import com.exchange.application.exception.SourceCurrencyCodeNotFoundException;
import com.exchange.application.exception.TargetCurrencyCodeNotFoundException;
import com.exchange.application.service.ConversionCommandService;
import com.exchange.application.service.ForeignExchangeRateService;
import com.exchange.application.type.ConversionType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
class ExchangeRateControllerTest {

    @InjectMocks
    private ExchangeRateController exchangeRateController;

    @Mock
    private ForeignExchangeRateService foreignExchangeRateService;

    @Mock
    private ConversionConverter conversionConverter;

    @Mock
    private ConversionCommandService conversionCommandService;

    @Test
    public void shouldGetAllExchangeRate() {
        Map<String, BigDecimal> map = new HashMap<>();
        map.put("TRY", new BigDecimal(1));
        map.put("USD", new BigDecimal(2));
        map.put("EUR", new BigDecimal(3));
        RateResponseDto rateResponseDto = new RateResponseDto();
        rateResponseDto.setRates(map);
        rateResponseDto.setBase("TRY");
        rateResponseDto.setSuccess(true);
        rateResponseDto.setTimestamp(1721969771000L);

        when(foreignExchangeRateService.fetchRateResponse()).thenReturn(rateResponseDto);

        ResponseEntity allExchangeRate = exchangeRateController.getAllExchangeRate();

        assertEquals(rateResponseDto, allExchangeRate.getBody());
        assertEquals(HttpStatus.OK, allExchangeRate.getStatusCode());
    }

    @Test
    public void shouldCatchExceptionWhenGetAllExchangeRate() {
        doThrow(RuntimeException.class).when(foreignExchangeRateService).fetchRateResponse();

        ResponseEntity response = null;

        try {
            response = exchangeRateController.getAllExchangeRate();
        } catch (Exception ex) {
            ExceptionResponse error = ExceptionResponse.createResultInfo(ex.getMessage(), "systemError");
            assertEquals(error, response.getBody());
            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        }
    }

    @Test
    public void shouldCalculateExchangeRate() {
        String sourceCurrencyCode = "USD";
        String targetCurrencyCode = "TRY";
        ConversionRequestDto conversionRequestDto = new ConversionRequestDto();
        conversionRequestDto.setSourceCurrencyCode(sourceCurrencyCode);
        conversionRequestDto.setTargetCurrencyCode(targetCurrencyCode);
        conversionRequestDto.setSourceAmount(null);
        conversionRequestDto.setConversionType(ConversionType.EXCHANGE_RATE);

        ConversionResponseDto conversionResponseDto = new ConversionResponseDto();
        conversionResponseDto.setConversionInfo("USD to TYR");
        conversionResponseDto.setConversionTime(new Date());
        conversionResponseDto.setConversionResult(new BigDecimal("33.05"));

        when(conversionConverter.prepareConversionRequestDto(sourceCurrencyCode, targetCurrencyCode, null, ConversionType.EXCHANGE_RATE)).thenReturn(conversionRequestDto);
        when(conversionCommandService.prepareRateCalculation(conversionRequestDto)).thenReturn(conversionResponseDto);

        ResponseEntity response = exchangeRateController.calculateExchangeRate(sourceCurrencyCode, targetCurrencyCode);

        assertEquals(conversionResponseDto, response.getBody());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void shouldCatchExceptionWhenSourceCurrencyCodeNotFound() {
        String sourceCurrencyCode = "USDD";
        String targetCurrencyCode = "TRY";
        ConversionType conversionType = ConversionType.EXCHANGE_RATE;
        ConversionRequestDto conversionRequestDto = new ConversionRequestDto();
        conversionRequestDto.setSourceCurrencyCode(sourceCurrencyCode);
        conversionRequestDto.setTargetCurrencyCode(targetCurrencyCode);
        conversionRequestDto.setSourceAmount(null);
        conversionRequestDto.setConversionType(conversionType);

        when(conversionConverter.prepareConversionRequestDto(sourceCurrencyCode, targetCurrencyCode, null, conversionType)).thenReturn(conversionRequestDto);
        doThrow(SourceCurrencyCodeNotFoundException.class).when(conversionCommandService).prepareRateCalculation(conversionRequestDto);

        ResponseEntity response = null;

        try {
            response = exchangeRateController.calculateExchangeRate(sourceCurrencyCode, targetCurrencyCode);
        } catch (Exception exception) {
            ExceptionResponse error = ExceptionResponse.createResultInfo(exception.getMessage(), "clientError");
            assertEquals(error, response.getBody());
            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        }
    }

    @Test
    public void shouldCatchExceptionWhenTargetCurrencyCodeNotFound() {
        String sourceCurrencyCode = "USD";
        String targetCurrencyCode = "TRYY";
        ConversionType conversionType = ConversionType.EXCHANGE_RATE;
        ConversionRequestDto conversionRequestDto = new ConversionRequestDto();
        conversionRequestDto.setSourceCurrencyCode(sourceCurrencyCode);
        conversionRequestDto.setTargetCurrencyCode(targetCurrencyCode);
        conversionRequestDto.setSourceAmount(null);
        conversionRequestDto.setConversionType(conversionType);

        when(conversionConverter.prepareConversionRequestDto(sourceCurrencyCode, targetCurrencyCode, null, conversionType)).thenReturn(conversionRequestDto);
        doThrow(TargetCurrencyCodeNotFoundException.class).when(conversionCommandService).prepareRateCalculation(conversionRequestDto);

        ResponseEntity response = null;

        try {
            response = exchangeRateController.calculateExchangeRate(sourceCurrencyCode, targetCurrencyCode);
        } catch (Exception exception) {
            ExceptionResponse error = ExceptionResponse.createResultInfo(exception.getMessage(), "clientError");
            assertEquals(error, response.getBody());
            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        }
    }
}

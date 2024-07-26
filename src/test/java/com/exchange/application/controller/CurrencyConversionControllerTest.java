package com.exchange.application.controller;

import com.exchange.application.converter.ConversionConverter;
import com.exchange.application.dto.ConversionRequestDto;
import com.exchange.application.dto.ConversionResponseDto;
import com.exchange.application.exception.ExceptionResponse;
import com.exchange.application.exception.SourceCurrencyCodeNotFoundException;
import com.exchange.application.exception.TargetCurrencyCodeNotFoundException;
import com.exchange.application.service.ConversionCommandService;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
class CurrencyConversionControllerTest {

    @InjectMocks
    private CurrencyConversionController currencyConversionController;

    @Mock
    private ConversionConverter conversionConverter;

    @Mock
    private ConversionCommandService conversionCommandService;

    @Test
    public void shouldCalculateCurrencyConversion() {
        String sourceCurrencyCode = "USD";
        String targetCurrencyCode = "TRY";
        BigDecimal sourceAmount = new BigDecimal(3);
        ConversionType conversionType = ConversionType.CURRENCY_CONVERSION;
        ConversionRequestDto conversionRequestDto = new ConversionRequestDto();
        conversionRequestDto.setSourceCurrencyCode(sourceCurrencyCode);
        conversionRequestDto.setTargetCurrencyCode(targetCurrencyCode);
        conversionRequestDto.setSourceAmount(sourceAmount);
        conversionRequestDto.setConversionType(conversionType);

        ConversionResponseDto conversionResponseDto = new ConversionResponseDto();
        conversionResponseDto.setConversionInfo("USD to TYR");
        conversionResponseDto.setConversionTime(new Date());
        conversionResponseDto.setConversionResult(new BigDecimal(99.14));

        when(conversionConverter.prepareConversionRequestDto(sourceCurrencyCode, targetCurrencyCode, sourceAmount, conversionType)).thenReturn(conversionRequestDto);
        when(conversionCommandService.prepareRateCalculation(conversionRequestDto)).thenReturn(conversionResponseDto);

        ResponseEntity result = currencyConversionController.calculateCurrencyConversion(sourceCurrencyCode, targetCurrencyCode, sourceAmount);

        assertEquals(conversionResponseDto, result.getBody());
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
    }

    @Test
    public void shouldCatchExceptionWhenSourceCurrencyCodeNotFound() {
        String sourceCurrencyCode = "USDD";
        String targetCurrencyCode = "TRY";
        BigDecimal sourceAmount = new BigDecimal(3);
        ConversionType conversionType = ConversionType.CURRENCY_CONVERSION;
        ConversionRequestDto conversionRequestDto = new ConversionRequestDto();
        conversionRequestDto.setSourceCurrencyCode(sourceCurrencyCode);
        conversionRequestDto.setTargetCurrencyCode(targetCurrencyCode);
        conversionRequestDto.setSourceAmount(sourceAmount);
        conversionRequestDto.setConversionType(conversionType);

        when(conversionConverter.prepareConversionRequestDto(sourceCurrencyCode, targetCurrencyCode, sourceAmount, conversionType)).thenReturn(conversionRequestDto);
        doThrow(SourceCurrencyCodeNotFoundException.class).when(conversionCommandService).prepareRateCalculation(conversionRequestDto);

        ResponseEntity response = null;

        try {
            response = currencyConversionController.calculateCurrencyConversion(sourceCurrencyCode, targetCurrencyCode, sourceAmount);
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
        BigDecimal sourceAmount = new BigDecimal(3);
        ConversionType conversionType = ConversionType.CURRENCY_CONVERSION;
        ConversionRequestDto conversionRequestDto = new ConversionRequestDto();
        conversionRequestDto.setSourceCurrencyCode(sourceCurrencyCode);
        conversionRequestDto.setTargetCurrencyCode(targetCurrencyCode);
        conversionRequestDto.setSourceAmount(sourceAmount);
        conversionRequestDto.setConversionType(conversionType);

        when(conversionConverter.prepareConversionRequestDto(sourceCurrencyCode, targetCurrencyCode, null, conversionType)).thenReturn(conversionRequestDto);
        doThrow(TargetCurrencyCodeNotFoundException.class).when(conversionCommandService).prepareRateCalculation(conversionRequestDto);

        ResponseEntity response = null;

        try {
            response = currencyConversionController.calculateCurrencyConversion(sourceCurrencyCode, targetCurrencyCode, sourceAmount);
        } catch (Exception exception) {
            ExceptionResponse error = ExceptionResponse.createResultInfo(exception.getMessage(), "clientError");
            assertEquals(error, response.getBody());
            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        }
    }

}

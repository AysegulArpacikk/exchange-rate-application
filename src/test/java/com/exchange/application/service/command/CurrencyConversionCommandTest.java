package com.exchange.application.service.command;

import com.exchange.application.converter.ConversionConverter;
import com.exchange.application.dto.ConversionResponseDto;
import com.exchange.application.entity.ConversionHistory;
import com.exchange.application.service.CalculateExchangeRateService;
import com.exchange.application.service.ConversionHistoryService;
import com.exchange.application.type.ConversionType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
class CurrencyConversionCommandTest {

    @InjectMocks
    private CurrencyConversionCommand command;

    @Mock
    private CalculateExchangeRateService calculateExchangeRateService;

    @Mock
    private ConversionConverter conversionConverter;

    @Mock
    private ConversionHistoryService conversionHistoryService;

    @Test
    public void shouldReturnTrueWhenRequestTypeIsCurrencyConversion() {
        ConversionContext context = new ConversionContext();
        context.setConversionType(ConversionType.CURRENCY_CONVERSION);

        assertTrue(command.canExecute(context));
    }

    @Test
    public void shouldExecuteCurrencyConversion() {
        String sourceCurrencyCode = "TRY";
        String targetCurrencyCode = "AZN";
        BigDecimal sourceAmount = new BigDecimal(5);
        ConversionContext context = new ConversionContext();
        context.setConversionType(ConversionType.CURRENCY_CONVERSION);
        context.setSourceCurrencyCode(sourceCurrencyCode);
        context.setTargetCurrencyCode(targetCurrencyCode);
        context.setSourceAmount(sourceAmount);
        ConversionResponseDto conversionResponseDto = ConversionResponseDto.builder()
                .conversionResult(new BigDecimal(5))
                .conversionInfo("5 TRY to AZN")
                .build();
        ConversionHistory conversionHistory = ConversionHistory.builder().build();

        when(calculateExchangeRateService.calculateExchangeRate(sourceCurrencyCode, targetCurrencyCode)).thenReturn(new BigDecimal(3));
        when(calculateExchangeRateService.calculateExchangeRate(sourceCurrencyCode, targetCurrencyCode).multiply(sourceAmount)).thenReturn(new BigDecimal(5));
        when(conversionConverter.prepareConversionResponseDto(sourceCurrencyCode, targetCurrencyCode, sourceAmount,
                calculateExchangeRateService.calculateExchangeRate(sourceCurrencyCode, targetCurrencyCode).multiply(sourceAmount))).thenReturn(conversionResponseDto);
        when(conversionConverter.convertResponseDtoToConversionHistory(conversionResponseDto)).thenReturn(conversionHistory);

        ConversionResponseDto result = command.execute(context);

        assertEquals(conversionResponseDto, result);
        verify(conversionHistoryService).saveHistory(conversionHistory);
    }
}

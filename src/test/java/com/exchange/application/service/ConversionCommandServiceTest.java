package com.exchange.application.service;

import com.exchange.application.converter.ConversionConverter;
import com.exchange.application.dto.ConversionRequestDto;
import com.exchange.application.dto.ConversionResponseDto;
import com.exchange.application.entity.ConversionHistory;
import com.exchange.application.service.command.ConversionCommands;
import com.exchange.application.service.command.CurrencyConversionCommand;
import com.exchange.application.service.command.ExchangeRateCommand;
import com.exchange.application.type.ConversionType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
class ConversionCommandServiceTest {

    @InjectMocks
    private ConversionCommandService conversionCommandService;

    @Spy
    private List<ConversionCommands> conversionCommandList = new ArrayList<>();

    @Spy
    @InjectMocks
    private CurrencyConversionCommand currencyConversionCommand;

    @Spy
    @InjectMocks
    private ExchangeRateCommand exchangeRateCommand;

    @Mock
    private CalculateExchangeRateService calculateExchangeRateService;

    @Mock
    private ConversionConverter conversionConverter;

    @Mock
    private ConversionHistoryService conversionHistoryService;


    @Test
    public void shouldPrepareRateCalculationWhenCurrencyConversion() {
        String sourceCurrencyCode = "TRY";
        String targetCurrencyCode = "AZN";
        BigDecimal sourceAmount = new BigDecimal(5);
        ConversionType conversionType = ConversionType.CURRENCY_CONVERSION;
        ConversionRequestDto conversionRequestDto = ConversionRequestDto.builder()
                .sourceCurrencyCode(sourceCurrencyCode)
                .targetCurrencyCode(targetCurrencyCode)
                .sourceAmount(sourceAmount)
                .conversionType(conversionType)
                .build();
        ConversionResponseDto conversionResponseDto = ConversionResponseDto.builder()
                .conversionInfo("5 TRY to AZN")
                .conversionTime(new Date())
                .conversionResult(new BigDecimal(25))
                .build();
        ConversionHistory conversionHistory = ConversionHistory.builder()
                .result(new BigDecimal(25))
                .info("5 TRY to AZN")
                .time(new Date())
                .build();
        conversionCommandList.add(currencyConversionCommand);

        when(calculateExchangeRateService.calculateExchangeRate(sourceCurrencyCode, targetCurrencyCode)).thenReturn(new BigDecimal(3));
        when(calculateExchangeRateService.calculateExchangeRate(sourceCurrencyCode, targetCurrencyCode).multiply(sourceAmount)).thenReturn(new BigDecimal(5));
        when(conversionConverter.prepareConversionResponseDto(sourceCurrencyCode, targetCurrencyCode, sourceAmount, new BigDecimal(25))).thenReturn(conversionResponseDto);
        when(conversionConverter.convertResponseDtoToConversionHistory(conversionResponseDto)).thenReturn(conversionHistory);

        ConversionResponseDto result = conversionCommandService.prepareRateCalculation(conversionRequestDto);

        assertEquals(conversionResponseDto, result);
        verify(conversionHistoryService).saveHistory(conversionHistory);
    }

    @Test
    public void shouldPrepareRateCalculationWhenExchangeRate() {
        String sourceCurrencyCode = "TRY";
        String targetCurrencyCode = "AZN";
        ConversionType conversionType = ConversionType.EXCHANGE_RATE;
        ConversionRequestDto conversionRequestDto = ConversionRequestDto.builder()
                .sourceCurrencyCode(sourceCurrencyCode)
                .targetCurrencyCode(targetCurrencyCode)
                .conversionType(conversionType)
                .build();
        ConversionResponseDto conversionResponseDto = ConversionResponseDto.builder()
                .conversionInfo("TRY to AZN")
                .conversionTime(new Date())
                .conversionResult(new BigDecimal(3))
                .build();
        ConversionHistory conversionHistory = ConversionHistory.builder()
                .result(new BigDecimal(3))
                .info("TRY to AZN")
                .time(new Date())
                .build();
        conversionCommandList.add(exchangeRateCommand);

        when(calculateExchangeRateService.calculateExchangeRate(sourceCurrencyCode, targetCurrencyCode)).thenReturn(new BigDecimal(3));
        when(conversionConverter.prepareConversionResponseDto(sourceCurrencyCode, targetCurrencyCode, null, new BigDecimal(3))).thenReturn(conversionResponseDto);
        when(conversionConverter.convertResponseDtoToConversionHistory(conversionResponseDto)).thenReturn(conversionHistory);

        ConversionResponseDto result = conversionCommandService.prepareRateCalculation(conversionRequestDto);

        assertEquals(conversionResponseDto, result);
        verify(conversionHistoryService).saveHistory(conversionHistory);
    }

}

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
class ExchangeRateCommandTest {

    @InjectMocks
    private ExchangeRateCommand command;

    @Mock
    private CalculateExchangeRateService calculateExchangeRateService;

    @Mock
    private ConversionConverter conversionConverter;

    @Mock
    private ConversionHistoryService conversionHistoryService;

    @Test
    public void shouldReturnTrueWhenRequestTypeIsExchangeRate() {
        ConversionContext context = new ConversionContext();
        context.setConversionType(ConversionType.EXCHANGE_RATE);

        assertTrue(command.canExecute(context));
    }

    @Test
    public void shouldExecuteExchangeRate() {
        String sourceCurrencyCode = "TRY";
        String targetCurrencyCode = "AZN";
        ConversionContext context = new ConversionContext();
        context.setConversionType(ConversionType.EXCHANGE_RATE);
        context.setSourceCurrencyCode(sourceCurrencyCode);
        context.setTargetCurrencyCode(targetCurrencyCode);
        ConversionResponseDto conversionResponseDto = ConversionResponseDto.builder()
                .conversionResult(new BigDecimal(25))
                .conversionInfo("TRY to AZN")
                .build();
        ConversionHistory conversionHistory = ConversionHistory.builder().build();

        when(calculateExchangeRateService.calculateExchangeRate(sourceCurrencyCode, targetCurrencyCode)).thenReturn(new BigDecimal(3));
        when(conversionConverter.prepareConversionResponseDto(sourceCurrencyCode, targetCurrencyCode, null,
                calculateExchangeRateService.calculateExchangeRate(sourceCurrencyCode, targetCurrencyCode))).thenReturn(conversionResponseDto);
        when(conversionConverter.convertResponseDtoToConversionHistory(conversionResponseDto)).thenReturn(conversionHistory);

        ConversionResponseDto result = command.execute(context);

        assertEquals(conversionResponseDto, result);
        verify(conversionHistoryService).saveHistory(conversionHistory);
    }
}

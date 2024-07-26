package com.exchange.application.service.command;

import com.exchange.application.dto.ConversionResponseDto;
import com.exchange.application.service.CalculateExchangeRateService;
import com.exchange.application.converter.ConversionConverter;
import com.exchange.application.type.ConversionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ExchangeRateCommand extends ConversionCommands {

    @Autowired
    private CalculateExchangeRateService calculateExchangeRateService;

    @Autowired
    private ConversionConverter conversionConverter;

    @Override
    public boolean canExecute(ConversionContext context) {
        return context.getConversionType().equals(ConversionType.EXCHANGE_RATE);
    }

    @Override
    public ConversionResponseDto execute(ConversionContext context) {
        String sourceCurrencyCode = context.getSourceCurrencyCode();
        String targetCurrencyCode = context.getTargetCurrencyCode();

        BigDecimal result = calculateExchangeRateService.calculateExchangeRate(sourceCurrencyCode, targetCurrencyCode);
        ConversionResponseDto conversionResponseDto = conversionConverter.prepareConversionResponseDto(sourceCurrencyCode, targetCurrencyCode, null, result);
        saveResultToConversionHistory(conversionResponseDto);
        return conversionResponseDto;
    }
}

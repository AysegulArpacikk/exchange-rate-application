package com.exchange.application.service.command;

import com.exchange.application.dto.ConversionResponseDto;
import com.exchange.application.service.CalculateExchangeRateService;
import com.exchange.application.service.ConversionConverter;
import com.exchange.application.type.ConversionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class CurrencyConversionCommand extends ConversionCommands {

    @Autowired
    private CalculateExchangeRateService calculateExchangeRateService;

    @Autowired
    private ConversionConverter conversionConverter;

    @Override
    public boolean canExecute(ConversionContext context) {
        return context.getConversionType().equals(ConversionType.CURRENCY_CONVERSION);
    }

    @Override
    public ConversionResponseDto execute(ConversionContext context) {
        String sourceCurrencyCode = context.getSourceCurrencyCode();
        String targetCurrencyCode = context.getTargetCurrencyCode();
        BigDecimal sourceAmount = context.getSourceAmount();

        BigDecimal result = calculateExchangeRateService.calculateExchangeRate(sourceCurrencyCode, targetCurrencyCode).multiply(sourceAmount);
        ConversionResponseDto conversionResponseDto = conversionConverter.prepareCalculateResponseDto(sourceCurrencyCode, targetCurrencyCode, sourceAmount, result);
        saveResultToConversionHistory(conversionResponseDto);
        return conversionResponseDto;
    }
}

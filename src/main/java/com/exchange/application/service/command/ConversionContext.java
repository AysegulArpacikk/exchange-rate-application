package com.exchange.application.service.command;

import com.exchange.application.dto.ConversionRequestDto;
import com.exchange.application.type.ConversionType;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ConversionContext {

    private String sourceCurrencyCode;
    private String targetCurrencyCode;
    private BigDecimal sourceAmount;
    private ConversionType conversionType;

    public static ConversionContext createContext(ConversionRequestDto conversionRequestDto) {
        ConversionContext context = new ConversionContext();
        context.setSourceCurrencyCode(conversionRequestDto.getSourceCurrencyCode());
        context.setTargetCurrencyCode(conversionRequestDto.getTargetCurrencyCode());
        context.setSourceAmount(conversionRequestDto.getSourceAmount());
        context.setConversionType(conversionRequestDto.getConversionType());
        return context;
    }
}

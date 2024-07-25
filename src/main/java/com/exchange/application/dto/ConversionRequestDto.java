package com.exchange.application.dto;

import com.exchange.application.type.ConversionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConversionRequestDto {

    String sourceCurrencyCode;
    String targetCurrencyCode;
    BigDecimal sourceAmount;
    ConversionType conversionType;
}

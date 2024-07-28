package com.exchange.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ConversionResponseDto {

    private String conversionInfo;
    private Date conversionTime;
    private BigDecimal conversionResult;
}

package com.exchange.application.service;

import com.exchange.application.dto.ConversionRequestDto;
import com.exchange.application.dto.ConversionResponseDto;
import com.exchange.application.entity.ConversionHistory;
import com.exchange.application.type.ConversionType;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class ConversionConverter {

    public ConversionRequestDto prepareConversionRequestDto(String sourceCurrencyCode,
                                                             String targetCurrencyCode,
                                                             BigDecimal sourceAmount,
                                                             ConversionType conversionType) {
        ConversionRequestDto conversionRequestDto = new ConversionRequestDto();
        conversionRequestDto.setSourceCurrencyCode(sourceCurrencyCode);
        conversionRequestDto.setTargetCurrencyCode(targetCurrencyCode);
        conversionRequestDto.setSourceAmount(sourceAmount);
        conversionRequestDto.setConversionType(conversionType);
        return conversionRequestDto;
    }

    public ConversionResponseDto prepareCalculateResponseDto(String sourceCurrencyCode,
                                                             String targetCurrencyCode,
                                                             BigDecimal sourceAmount,
                                                             BigDecimal result) {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        StringBuilder sb = new StringBuilder();
        if (sourceAmount != null) {
            sb.append(sourceAmount).append(" ");
        }
        sb.append(sourceCurrencyCode).append(" to ").append(targetCurrencyCode);
        ConversionResponseDto conversionResponseDto = new ConversionResponseDto();
        conversionResponseDto.setConversionResult(result);
        conversionResponseDto.setConversionTime(date);
        conversionResponseDto.setConversionInfo(sb.toString());
        return conversionResponseDto;
    }

    public ConversionHistory convertResponseDtoToCalculationHistory(ConversionResponseDto conversionResponseDto) {
        ConversionHistory conversionHistory = new ConversionHistory();
        conversionHistory.setConversionResult(conversionResponseDto.getConversionResult());
        conversionHistory.setConversionTime(conversionResponseDto.getConversionTime());
        conversionHistory.setConversionInfo(conversionResponseDto.getConversionInfo());
        return conversionHistory;
    }
}

package com.exchange.application.converter;

import com.exchange.application.dto.ConversionRequestDto;
import com.exchange.application.dto.ConversionResponseDto;
import com.exchange.application.dto.PagingDto;
import com.exchange.application.entity.ConversionHistory;
import com.exchange.application.type.ConversionType;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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

    public ConversionResponseDto prepareConversionResponseDto(String sourceCurrencyCode,
                                                              String targetCurrencyCode,
                                                              BigDecimal sourceAmount,
                                                              BigDecimal result) {
        Date date = new Date();
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

    public ConversionHistory convertResponseDtoToConversionHistory(ConversionResponseDto conversionResponseDto) {
        ConversionHistory conversionHistory = new ConversionHistory();
        conversionHistory.setResult(conversionResponseDto.getConversionResult());
        conversionHistory.setTime(conversionResponseDto.getConversionTime());
        conversionHistory.setInfo(conversionResponseDto.getConversionInfo());
        return conversionHistory;
    }

    public PagingDto preparePagingDto(Integer pageNo, Integer pageSize, String sortBy, String sortDirection) {
        PagingDto pagingDto = new PagingDto();
        pagingDto.setPageNo(pageNo);
        pagingDto.setPageSize(pageSize);
        pagingDto.setSortBy(sortBy);
        pagingDto.setSortDirection(sortDirection);
        return pagingDto;
    }
}

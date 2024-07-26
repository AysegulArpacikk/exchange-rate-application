package com.exchange.application.converter;

import com.exchange.application.dto.ConversionRequestDto;
import com.exchange.application.dto.ConversionResponseDto;
import com.exchange.application.dto.PagingDto;
import com.exchange.application.entity.ConversionHistory;
import com.exchange.application.type.ConversionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
class ConversionConverterTest {

    @InjectMocks
    private ConversionConverter conversionConverter;

    private String sourceCurrencyCode;
    private String targetCurrencyCode;
    private BigDecimal sourceAmount;
    private BigDecimal result;
    private ConversionType conversionType;
    private ConversionRequestDto conversionRequestDto;
    private ConversionResponseDto conversionResponseDto;

    @BeforeEach
    void init() {
        sourceCurrencyCode = "TRY";
        targetCurrencyCode = "AZN";
        sourceAmount = new BigDecimal(5);
        result = new BigDecimal(10);
        conversionType = ConversionType.CURRENCY_CONVERSION;
        conversionRequestDto = new ConversionRequestDto();
        conversionResponseDto = new ConversionResponseDto();
    }

    @Test
    public void shouldPrepareConversionRequestDto() {
        conversionRequestDto.setSourceCurrencyCode(sourceCurrencyCode);
        conversionRequestDto.setTargetCurrencyCode(targetCurrencyCode);
        conversionRequestDto.setSourceAmount(sourceAmount);
        conversionRequestDto.setConversionType(conversionType);

        ConversionRequestDto result = conversionConverter.prepareConversionRequestDto(sourceCurrencyCode, targetCurrencyCode, sourceAmount, conversionType);

        assertEquals(conversionRequestDto, result);
    }

    @Test
    public void shouldPrepareConversionResponseDto() {
        conversionResponseDto.setConversionResult(result);
        conversionResponseDto.setConversionTime(new Date());
        conversionResponseDto.setConversionInfo("5 TRY to AZN");

        ConversionResponseDto response = conversionConverter.prepareConversionResponseDto(sourceCurrencyCode, targetCurrencyCode, sourceAmount, result);

        assertEquals(conversionResponseDto, response);
    }

    @Test
    public void shouldPrepareConversionResponseDtoWhenSourceAmountIsNull() {
        conversionResponseDto.setConversionResult(result);
        conversionResponseDto.setConversionTime(new Date());
        conversionResponseDto.setConversionInfo("TRY to AZN");

        ConversionResponseDto response = conversionConverter.prepareConversionResponseDto(sourceCurrencyCode, targetCurrencyCode, null, result);

        assertEquals(conversionResponseDto, response);
    }

    @Test
    public void shouldConvertResponseDtoToConversionHistory() {
        conversionResponseDto.setConversionResult(result);
        conversionResponseDto.setConversionTime(new Date());
        conversionResponseDto.setConversionInfo("TRY to AZN");

        ConversionHistory history = new ConversionHistory();
        history.setInfo(conversionResponseDto.getConversionInfo());
        history.setResult(conversionResponseDto.getConversionResult());
        history.setTime(conversionResponseDto.getConversionTime());

        ConversionHistory result = conversionConverter.convertResponseDtoToConversionHistory(conversionResponseDto);

        assertEquals(history, result);
    }

    @Test
    public void shouldPreparePagingDto() {
        PagingDto pagingDto = new PagingDto();
        pagingDto.setPageNo(1);
        pagingDto.setPageSize(10);
        pagingDto.setSortBy("time");
        pagingDto.setSortDirection("asc");

        PagingDto result = conversionConverter.preparePagingDto(1, 10, "time", "asc");

        assertEquals(pagingDto, result);
    }
}

package com.exchange.application.controller;

import com.exchange.application.dto.ConversionRequestDto;
import com.exchange.application.dto.ConversionResponseDto;
import com.exchange.application.service.ConversionCommandService;
import com.exchange.application.service.ConversionConverter;
import com.exchange.application.type.ConversionType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1/currency/conversion")
@RequiredArgsConstructor
public class CurrencyConversionController {

    private final ConversionConverter conversionConverter;
    private final ConversionCommandService conversionCommandService;

    @GetMapping()
    public ResponseEntity calculateCurrencyConversion(@RequestParam String sourceCurrencyCode,
                                                      @RequestParam String targetCurrencyCode,
                                                      @RequestParam BigDecimal sourceAmount) {
        ConversionRequestDto conversionRequestDto = conversionConverter.prepareConversionRequestDto(sourceCurrencyCode, targetCurrencyCode, sourceAmount, ConversionType.CURRENCY_CONVERSION);
        ConversionResponseDto conversionResponseDto = conversionCommandService.prepareRateCalculation(conversionRequestDto);
        return ResponseEntity.ok(conversionResponseDto);
    }

}

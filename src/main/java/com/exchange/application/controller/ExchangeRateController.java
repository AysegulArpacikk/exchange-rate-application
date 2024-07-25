package com.exchange.application.controller;

import com.exchange.application.converter.ConversionConverter;
import com.exchange.application.dto.ConversionRequestDto;
import com.exchange.application.dto.ConversionResponseDto;
import com.exchange.application.dto.RateResponseDto;
import com.exchange.application.service.*;
import com.exchange.application.type.ConversionType;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/v1/exchange")
@RequiredArgsConstructor
public class ExchangeRateController {

    private final ExchangeRateService exchangeRateService;
    private final ConversionConverter conversionConverter;
    private final ConversionCommandService conversionCommandService;

    @GetMapping("/rates")
    @Operation(summary = "Exchange rates list", description = "This method list exchange rate from foreign service")
    public ResponseEntity getAllExchangeRate() {
        RateResponseDto rateResponseDto = exchangeRateService.rateResponse();
        return ResponseEntity.ok(rateResponseDto);
    }

    @GetMapping("/calculateRate")
    @Operation(summary = "Calculate exchange rate", description = "This method calculate exchange rate between two currency")
    public ResponseEntity calculateExchangeRate(@RequestParam String sourceCurrencyCode, @RequestParam String targetCurrencyCode) {
        ConversionRequestDto conversionRequestDto = conversionConverter.prepareConversionRequestDto(sourceCurrencyCode, targetCurrencyCode, null, ConversionType.EXCHANGE_RATE);
        ConversionResponseDto conversionResponseDto = conversionCommandService.prepareRateCalculation(conversionRequestDto);
        return ResponseEntity.ok(conversionResponseDto);
    }
}

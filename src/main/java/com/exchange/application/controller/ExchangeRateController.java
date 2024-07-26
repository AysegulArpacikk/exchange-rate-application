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
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/exchange/rate")
@RequiredArgsConstructor
public class ExchangeRateController extends ConversionBaseController {

    private final ForeignExchangeRateService foreignExchangeRateService;
    private final ConversionConverter conversionConverter;
    private final ConversionCommandService conversionCommandService;

    @GetMapping()
    @Operation(summary = "Exchange rates list", description = "This method list exchange rate from foreign service")
    public ResponseEntity getAllExchangeRate() {
        RateResponseDto rateResponseDto = foreignExchangeRateService.rateResponse();
        return ResponseEntity.ok(rateResponseDto);
    }

    @PostMapping("/calculate")
    @Operation(summary = "Calculate exchange rate", description = "This method calculate exchange rate between two currency and then save to history table.")
    public ResponseEntity calculateExchangeRate(@RequestParam String sourceCurrencyCode, @RequestParam String targetCurrencyCode) {
        try {
            ConversionRequestDto conversionRequestDto = conversionConverter.prepareConversionRequestDto(sourceCurrencyCode, targetCurrencyCode, null, ConversionType.EXCHANGE_RATE);
            ConversionResponseDto conversionResponseDto = conversionCommandService.prepareRateCalculation(conversionRequestDto);
            return ResponseEntity.ok(conversionResponseDto);
        } catch (Exception exception) {
            return ResponseEntity.badRequest().body(setExceptionMessage(exception));
        }
    }
}

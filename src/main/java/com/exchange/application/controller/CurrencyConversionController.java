package com.exchange.application.controller;

import com.exchange.application.dto.ConversionRequestDto;
import com.exchange.application.dto.ConversionResponseDto;
import com.exchange.application.service.ConversionCommandService;
import com.exchange.application.converter.ConversionConverter;
import com.exchange.application.type.ConversionType;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1/currency/conversion")
@RequiredArgsConstructor
public class CurrencyConversionController extends ConversionBaseController {

    private final ConversionConverter conversionConverter;
    private final ConversionCommandService conversionCommandService;

    @PostMapping()
    @Operation(summary = "Calculate currency conversion", description = "This method calculate currency conversion between two currency and then save to history table.")
    public ResponseEntity calculateCurrencyConversion(@RequestParam String sourceCurrencyCode,
                                                      @RequestParam String targetCurrencyCode,
                                                      @RequestParam BigDecimal sourceAmount) {
        try {
            ConversionRequestDto conversionRequestDto = conversionConverter.prepareConversionRequestDto(sourceCurrencyCode, targetCurrencyCode, sourceAmount, ConversionType.CURRENCY_CONVERSION);
            ConversionResponseDto conversionResponseDto = conversionCommandService.prepareRateCalculation(conversionRequestDto);
            return ResponseEntity.ok(conversionResponseDto);
        } catch (Exception exception) {
            return ResponseEntity.badRequest().body(setExceptionMessage(exception));
        }
    }

}

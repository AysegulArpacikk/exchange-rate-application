package com.exchange.application.controller;

import com.exchange.application.converter.ConversionConverter;
import com.exchange.application.dto.PagingDto;
import com.exchange.application.entity.ConversionHistory;
import com.exchange.application.service.ConversionHistoryService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/conversionHistory")
@RequiredArgsConstructor
public class ConversionHistoryController extends ConversionBaseController {

    private final ConversionHistoryService conversionHistoryService;
    private final ConversionConverter conversionConverter;

    @GetMapping()
    @Operation(summary = "Get conversion history", description = "This method get all conversion history according to not required parameters.")
    public ResponseEntity getConversionHistory(@RequestParam(required = false) Integer pageNo,
                                                @RequestParam(required = false) Integer pageSize,
                                                @RequestParam(required = false) String sortBy,
                                                @RequestParam(required = false) String sortDirection,
                                                @RequestParam(required = false) Long startDate,
                                                @RequestParam(required = false) Long endDate) {
        try {
            PagingDto pagingDto = conversionConverter.preparePagingDto(pageNo, pageSize, sortBy, sortDirection);
            List<ConversionHistory> conversionHistory = conversionHistoryService.getCalculationHistory(pagingDto, startDate, endDate);
            return ResponseEntity.ok(conversionHistory);
        } catch (Exception exception) {
            return ResponseEntity.badRequest().body(setExceptionMessage(exception));
        }
    }
}

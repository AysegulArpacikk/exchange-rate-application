package com.exchange.application.controller;

import com.exchange.application.converter.ConversionConverter;
import com.exchange.application.dto.PagingDto;
import com.exchange.application.entity.ConversionHistory;
import com.exchange.application.service.ConversionHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/calculationHistories")
@RequiredArgsConstructor
public class ConversionHistoryController {

    private final ConversionHistoryService conversionHistoryService;
    private final ConversionConverter conversionConverter;

    @GetMapping()
    public ResponseEntity getCalculationHistory(@RequestParam(required = false) Integer pageNo,
                                                @RequestParam(required = false) Integer pageSize,
                                                @RequestParam(required = false) String sortBy,
                                                @RequestParam(required = false) String sortDirection,
                                                @RequestParam(required = false) Long startDate,
                                                @RequestParam(required = false) Long endDate) {
        PagingDto pagingDto = conversionConverter.preparePagingDto(pageNo, pageSize, sortBy, sortDirection);
        List<ConversionHistory> conversionHistory = conversionHistoryService.getCalculationHistory(pagingDto, startDate, endDate);
        return ResponseEntity.ok(conversionHistory);
    }
}

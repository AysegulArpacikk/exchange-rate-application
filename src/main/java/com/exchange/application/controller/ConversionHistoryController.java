package com.exchange.application.controller;

import com.exchange.application.entity.ConversionHistory;
import com.exchange.application.service.ConversionHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1/calculationHistories")
@RequiredArgsConstructor
public class ConversionHistoryController {

    private final ConversionHistoryService conversionHistoryService;

    @GetMapping()
    public ResponseEntity getCalculationHistory(@RequestParam Date startDate, @RequestParam Date endDate) {
        List<ConversionHistory> conversionHistory = conversionHistoryService.getCalculationHistory(startDate, endDate);
        return ResponseEntity.ok(conversionHistory);
    }
}

package com.exchange.application.service.command;

import com.exchange.application.dto.ConversionResponseDto;
import com.exchange.application.entity.ConversionHistory;
import com.exchange.application.converter.ConversionConverter;
import com.exchange.application.service.ConversionHistoryService;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class ConversionCommands {

    @Autowired
    private ConversionConverter conversionConverter;

    @Autowired
    private ConversionHistoryService conversionHistoryService;

    public abstract boolean canExecute(ConversionContext context);

    public abstract ConversionResponseDto execute(ConversionContext context);

    public void saveResultToConversionHistory(ConversionResponseDto conversionResponseDto) {
        ConversionHistory conversionHistory = conversionConverter.convertResponseDtoToCalculationHistory(conversionResponseDto);
        conversionHistoryService.saveHistory(conversionHistory);
    }
}

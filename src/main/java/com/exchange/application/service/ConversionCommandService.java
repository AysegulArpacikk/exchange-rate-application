package com.exchange.application.service;

import com.exchange.application.dto.ConversionRequestDto;
import com.exchange.application.dto.ConversionResponseDto;
import com.exchange.application.service.command.ConversionContext;
import com.exchange.application.service.command.ConversionCommands;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConversionCommandService {

    @Autowired
    private List<ConversionCommands> conversionCommandList;

    public ConversionResponseDto prepareRateCalculation(ConversionRequestDto conversionRequestDto) {
        ConversionContext context = ConversionContext.createContext(conversionRequestDto);
        ConversionResponseDto conversionResponseDto = new ConversionResponseDto();
        for (ConversionCommands command : conversionCommandList) {
            if (command.canExecute(context)) {
                conversionResponseDto = command.execute(context);
            }
        }
        return conversionResponseDto;
    }

}

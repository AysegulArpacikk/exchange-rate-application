package com.exchange.application.controller;

import com.exchange.application.converter.ConversionConverter;
import com.exchange.application.dto.PagingDto;
import com.exchange.application.entity.ConversionHistory;
import com.exchange.application.exception.ExceptionResponse;
import com.exchange.application.exception.InvalidDateRequestException;
import com.exchange.application.service.ConversionHistoryService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
public class ConversionHistoryControllerTest {

    @InjectMocks
    private ConversionHistoryController conversionHistoryController;

    @Mock
    private ConversionHistoryService conversionHistoryService;

    @Mock
    private ConversionConverter conversionConverter;

    private PagingDto pagingDto;
    private List<ConversionHistory> conversionHistoryList;

    @Before
    public void setup() {
        pagingDto = new PagingDto();
        conversionHistoryList = new ArrayList<>();
    }

    @Test
    public void shouldGetConversionHistory() {
        ConversionHistory history1 = new ConversionHistory();
        history1.setInfo("AZN to TRY");
        history1.setResult(new BigDecimal(5));
        ConversionHistory history2 = new ConversionHistory();
        history2.setInfo("TRY to AZN");
        history2.setResult(new BigDecimal(3));
        conversionHistoryList.add(history1);
        conversionHistoryList.add(history2);

        when(conversionConverter.preparePagingDto(1, 10, "date", "asc")).thenReturn(pagingDto);
        when(conversionHistoryService.getConversionHistory(pagingDto, 1609459200000L, 1612137600000L))
                .thenReturn(conversionHistoryList);

        ResponseEntity response = conversionHistoryController.getConversionHistory(1, 10, "date", "asc", 1609459200000L, 1612137600000L);

        assertEquals(ResponseEntity.ok(conversionHistoryList), response);
        verify(conversionConverter).preparePagingDto(1, 10, "date", "asc");
        verify(conversionHistoryService).getConversionHistory(pagingDto, 1609459200000L, 1612137600000L);
    }

    @Test
    public void shouldCatchExceptionWhenTargetCurrencyCodeNotFound() {
        Long startDate = 1721751708000L;
        Long endDate = 1722097308000L;
        ExceptionResponse error = ExceptionResponse.createResultInfo(null, "clientError");

        when(conversionConverter.preparePagingDto(1, 10, "date", "asc")).thenReturn(pagingDto);
        doThrow(InvalidDateRequestException.class).when(conversionHistoryService).getConversionHistory(pagingDto, startDate, endDate);

        ResponseEntity response = conversionHistoryController.getConversionHistory(1, 10, "date", "asc", startDate, endDate);

        assertEquals(error.getErrorCode(), ((ExceptionResponse) Objects.requireNonNull(response.getBody())).getErrorCode());
        assertEquals(error.getErrorMessage(), ((ExceptionResponse) Objects.requireNonNull(response.getBody())).getErrorMessage());
        assertEquals(error.getStatus(), ((ExceptionResponse) Objects.requireNonNull(response.getBody())).getStatus());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}

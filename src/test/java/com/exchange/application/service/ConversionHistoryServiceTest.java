package com.exchange.application.service;

import com.exchange.application.dao.ConversionHistoryDao;
import com.exchange.application.dto.PagingDto;
import com.exchange.application.entity.ConversionHistory;
import com.exchange.application.exception.InvalidDateRequestException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
class ConversionHistoryServiceTest {

    @InjectMocks
    private ConversionHistoryService conversionHistoryService;

    @Mock
    private ConversionHistoryDao conversionHistoryDao;

    @Test
    public void shouldSaveConversionHistory() {
        ConversionHistory conversionHistory = new ConversionHistory();

        conversionHistoryService.saveHistory(conversionHistory);

        verify(conversionHistoryDao).save(conversionHistory);
    }

    @Test
    public void shouldGetConversionHistoryWhenAllParametersAreNull() {
        PagingDto pagingDto = PagingDto.builder().pageNo(0).pageSize(200).sortBy("time").sortDirection("desc").build();
        Sort sort = Sort.by(Sort.Direction.fromString(pagingDto.getSortDirection()), pagingDto.getSortBy());
        Pageable pageable = PageRequest.of(pagingDto.getPageNo(), pagingDto.getPageSize(), sort);
        ConversionHistory conversionHistory1 = ConversionHistory.builder().id(1L).time(new Date()).info("TRY to AZN").result(new BigDecimal(5)).build();
        ConversionHistory conversionHistory2 = ConversionHistory.builder().id(2L).time(new Date()).info("AZN to TRY").result(new BigDecimal(10)).build();
        List<ConversionHistory> conversionHistoryList = Arrays.asList(conversionHistory1, conversionHistory2);
        Page<ConversionHistory> conversionHistoryPage = Mockito.mock(Page.class);

        when(conversionHistoryDao.findAll(pageable)).thenReturn(conversionHistoryPage);
        when(conversionHistoryDao.findAll(pageable).getContent()).thenReturn(conversionHistoryList);

        List<ConversionHistory> result = conversionHistoryService.getConversionHistory(new PagingDto(), null, null);

        assertEquals(conversionHistoryList, result);
    }

    @Test
    public void shouldGetConversionHistoryWhenStartDateIsNullAndEndDateIsNotNull() {
        long endDate = 1722062397000L;
        Date endConversionDate = new Date(endDate);
        long historyDate = 1721975997000L;
        PagingDto pagingDto = PagingDto.builder().pageNo(0).pageSize(200).sortBy("time").sortDirection("desc").build();
        Sort sort = Sort.by(Sort.Direction.fromString(pagingDto.getSortDirection()), pagingDto.getSortBy());
        Pageable pageable = PageRequest.of(pagingDto.getPageNo(), pagingDto.getPageSize(), sort);
        ConversionHistory conversionHistory1 = ConversionHistory.builder().id(1L).time(new Date(historyDate)).info("TRY to AZN").result(new BigDecimal(5)).build();
        ConversionHistory conversionHistory2 = ConversionHistory.builder().id(2L).time(new Date(historyDate)).info("AZN to TRY").result(new BigDecimal(10)).build();
        List<ConversionHistory> conversionHistoryList = Arrays.asList(conversionHistory1, conversionHistory2);
        Page<ConversionHistory> conversionHistoryPage = Mockito.mock(Page.class);

        when(conversionHistoryDao.findByTimeBefore(endConversionDate, pageable)).thenReturn(conversionHistoryPage);
        when(conversionHistoryDao.findByTimeBefore(endConversionDate, pageable).getContent()).thenReturn(conversionHistoryList);

        List<ConversionHistory> result = conversionHistoryService.getConversionHistory(new PagingDto(), null, endDate);

        assertEquals(conversionHistoryList, result);
    }

    @Test
    public void shouldGetConversionHistoryWhenEndDateIsNullAndStartDateIsNotNull() {
        long startDate = 1721975997000L;
        Date startConversionDate = new Date(startDate);
        long historyDate = 1722062397000L;
        PagingDto pagingDto = PagingDto.builder().pageNo(0).pageSize(200).sortBy("time").sortDirection("desc").build();
        Sort sort = Sort.by(Sort.Direction.fromString(pagingDto.getSortDirection()), pagingDto.getSortBy());
        Pageable pageable = PageRequest.of(pagingDto.getPageNo(), pagingDto.getPageSize(), sort);
        ConversionHistory conversionHistory1 = ConversionHistory.builder().id(1L).time(new Date(historyDate)).info("TRY to AZN").result(new BigDecimal(5)).build();
        ConversionHistory conversionHistory2 = ConversionHistory.builder().id(2L).time(new Date(historyDate)).info("AZN to TRY").result(new BigDecimal(10)).build();
        List<ConversionHistory> conversionHistoryList = Arrays.asList(conversionHistory1, conversionHistory2);
        Page<ConversionHistory> conversionHistoryPage = Mockito.mock(Page.class);

        when(conversionHistoryDao.findByTimeAfter(startConversionDate, pageable)).thenReturn(conversionHistoryPage);
        when(conversionHistoryDao.findByTimeAfter(startConversionDate, pageable).getContent()).thenReturn(conversionHistoryList);

        List<ConversionHistory> result = conversionHistoryService.getConversionHistory(new PagingDto(), startDate, null);

        assertEquals(conversionHistoryList, result);
    }

    @Test
    public void shouldGetConversionHistoryWithPaginationWhenStartDateAndEndDateAreNotNull() {
        long startDate = 1721803197000L;
        long endDate = 1722062397000L;
        Date startConversionDate = new Date(startDate);
        Date endConversionDate = new Date(endDate);
        long historyDate1 = 1721975997000L;
        long historyDate2 = 1721979597000L;
        PagingDto pagingDto = PagingDto.builder().pageNo(1).pageSize(1).sortBy("time").sortDirection("asc").build();
        Sort sort = Sort.by(Sort.Direction.fromString(pagingDto.getSortDirection()), pagingDto.getSortBy());
        Pageable pageable = PageRequest.of(pagingDto.getPageNo(), pagingDto.getPageSize(), sort);
        ConversionHistory conversionHistory1 = ConversionHistory.builder().id(1L).time(new Date(historyDate1)).info("TRY to AZN").result(new BigDecimal(5)).build();
        ConversionHistory conversionHistory2 = ConversionHistory.builder().id(2L).time(new Date(historyDate2)).info("AZN to TRY").result(new BigDecimal(10)).build();
        List<ConversionHistory> conversionHistoryList = List.of(conversionHistory2);
        Page<ConversionHistory> conversionHistoryPage = Mockito.mock(Page.class);

        when(conversionHistoryDao.findByTimeBetween(startConversionDate, endConversionDate, pageable)).thenReturn(conversionHistoryPage);
        when(conversionHistoryDao.findByTimeBetween(startConversionDate, endConversionDate, pageable).getContent()).thenReturn(conversionHistoryList);

        List<ConversionHistory> result = conversionHistoryService.getConversionHistory(pagingDto, startDate, endDate);

        assertEquals(conversionHistoryList, result);
    }

    @Test
    public void shouldThrowExceptionWhenStartDateIsAfterThenEndDate() {
        long startDate = 1722062397000L;
        long endDate = 1721803197000L;

        Exception exception = assertThrows(InvalidDateRequestException.class, () -> {
            conversionHistoryService.getConversionHistory(new PagingDto(), startDate, endDate);
        });

        String expectedMessage = "The start date cannot be later than the end date.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}

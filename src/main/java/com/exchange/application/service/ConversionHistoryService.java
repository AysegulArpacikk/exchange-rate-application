package com.exchange.application.service;

import com.exchange.application.dao.ConversionHistoryDao;
import com.exchange.application.dto.PagingDto;
import com.exchange.application.entity.ConversionHistory;
import com.exchange.application.exception.ExceptionResponse;
import com.exchange.application.exception.InvalidDateRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class ConversionHistoryService {

    private static final int DEFAULT_PAGE_NO = 0;
    private static final int DEFAULT_PAGE_SIZE = 200;
    private static final String DEFAULT_LIST_SORT_CRITERIA = "time";
    private static final String DEFAULT_LIST_SORT_DIRECTION = "DESC";

    @Autowired
    private ConversionHistoryDao conversionHistoryDao;

    @Transactional
    public void saveHistory(ConversionHistory conversionHistory) {
        conversionHistoryDao.save(conversionHistory);
    }

    public List<ConversionHistory> getCalculationHistory(PagingDto pagingDto, Long startDate, Long endDate) {
        generatePagingDefaultValue(pagingDto);
        Pageable pageable = preparePageable(pagingDto);
        Date startConversionDate = null;
        Date endConversionDate = null;
        boolean isStartDateAfterThanEndDate = startDate > endDate;

        if (Objects.nonNull(startDate)) {
            startConversionDate = new Date(startDate);
        } if (Objects.nonNull(endDate)) {
            endConversionDate = new Date(endDate);
        }

        return prepareHistoryListByDate(startConversionDate, endConversionDate, pageable, isStartDateAfterThanEndDate);
    }

    private List<ConversionHistory> prepareHistoryListByDate(Date startDate, Date endDate, Pageable pageable, boolean isStartDateAfterThanEndDate) {
        if (Objects.nonNull(startDate) && Objects.nonNull(endDate)) {
            if (isStartDateAfterThanEndDate) {
                throw new InvalidDateRequestException(ExceptionResponse.WRONG_DATE_REQUEST);
            }
            return conversionHistoryDao.findByTimeBetween(startDate, endDate, pageable).getContent();
        } else if (Objects.isNull(startDate) && Objects.nonNull(endDate)) {
            return conversionHistoryDao.findByTimeBefore(endDate, pageable).getContent();
        } else if (Objects.nonNull(startDate) && Objects.isNull(endDate)) {
            return conversionHistoryDao.findByTimeAfter(startDate, pageable).getContent();
        }
        return conversionHistoryDao.findAll(pageable).getContent();
    }

    private Pageable preparePageable(PagingDto pagingDto) {
        Sort sort = Sort.by(Sort.Direction.fromString(pagingDto.getSortDirection()), pagingDto.getSortBy());
        return PageRequest.of(pagingDto.getPageNo(), pagingDto.getPageSize(), sort);
    }

    private void generatePagingDefaultValue(PagingDto pagingDto) {
        if (pagingDto.getPageNo() == null) {
            pagingDto.setPageNo(DEFAULT_PAGE_NO);
        }
        if (pagingDto.getPageSize() == null) {
            pagingDto.setPageSize(DEFAULT_PAGE_SIZE);
        }
        if (pagingDto.getSortBy() == null) {
            pagingDto.setSortBy(DEFAULT_LIST_SORT_CRITERIA);
        }
        if (pagingDto.getSortDirection() == null) {
            pagingDto.setSortDirection(DEFAULT_LIST_SORT_DIRECTION);
        }
    }
}

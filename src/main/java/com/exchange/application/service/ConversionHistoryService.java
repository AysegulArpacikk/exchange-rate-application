package com.exchange.application.service;

import com.exchange.application.dao.ConversionHistoryDao;
import com.exchange.application.dto.PagingDto;
import com.exchange.application.entity.ConversionHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class ConversionHistoryService {

    @Autowired
    private ConversionHistoryDao conversionHistoryDao;

    public void saveHistory(ConversionHistory conversionHistory) {
        conversionHistoryDao.save(conversionHistory);
    }

    public List<ConversionHistory> getCalculationHistory(PagingDto pagingDto, Long startDate, Long endDate) {
        generatePagingDefaultValue(pagingDto);
        Pageable pageable = preparePageable(pagingDto);
        Date startConversionDate = null;
        Date endConversionDate = null;

        if (Objects.nonNull(startDate)) {
            startConversionDate = new Date(startDate);
        } if (Objects.nonNull(endDate)) {
            endConversionDate = new Date(endDate);
        }

        if (Objects.nonNull(startConversionDate) && Objects.nonNull(endConversionDate)) {
            return conversionHistoryDao.findByTimeBetween(startConversionDate, endConversionDate, pageable).getContent();
        } else if (Objects.isNull(startConversionDate) && Objects.nonNull(endConversionDate)) {
            return conversionHistoryDao.findByTimeBefore(endConversionDate, pageable).getContent();
        } else if (Objects.nonNull(startConversionDate) && Objects.isNull(endConversionDate)) {
            return conversionHistoryDao.findByTimeAfter(startConversionDate, pageable).getContent();
        }
        return conversionHistoryDao.findAll(pageable).getContent();
    }

    private Pageable preparePageable(PagingDto pagingDto) {
        Sort sort = Sort.by(Sort.Direction.fromString(pagingDto.getSortDirection()), pagingDto.getSortBy());
        return PageRequest.of(pagingDto.getPageNo(), pagingDto.getPageSize(), sort);
    }

    private void generatePagingDefaultValue(PagingDto pagingDto) {
        if (pagingDto.getPageNo() == null) {
            pagingDto.setPageNo(0);
        }
        if (pagingDto.getPageSize() == null) {
            pagingDto.setPageSize(200);
        }
        if (pagingDto.getSortBy() == null) {
            pagingDto.setSortBy("time");
        }
        if (pagingDto.getSortDirection() == null) {
            pagingDto.setSortDirection("DESC");
        }
    }
}

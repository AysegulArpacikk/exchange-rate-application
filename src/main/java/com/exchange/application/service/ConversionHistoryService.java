package com.exchange.application.service;

import com.exchange.application.dao.ConversionHistoryDao;
import com.exchange.application.entity.ConversionHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ConversionHistoryService {

    @Autowired
    private ConversionHistoryDao conversionHistoryDao;

    public void saveHistory(ConversionHistory conversionHistory) {
        conversionHistoryDao.save(conversionHistory);
    }

    public List<ConversionHistory> getCalculationHistory(Date startDate, Date endDate) {
        return conversionHistoryDao.getAllBetweenDates(startDate, endDate);
    }
}

package com.exchange.application.dao;

import com.exchange.application.entity.ConversionHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface ConversionHistoryDao extends JpaRepository<ConversionHistory, Long> {

    Page<ConversionHistory> findByTimeBefore(Date endDate, Pageable paging);

    Page<ConversionHistory> findByTimeAfter(Date startDate, Pageable paging);

    Page<ConversionHistory> findByTimeBetween(Date startDate, Date endDate, Pageable paging);

}

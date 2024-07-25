package com.exchange.application.dao;

import com.exchange.application.entity.ConversionHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ConversionHistoryDao extends JpaRepository<ConversionHistory, Long> {

    @Query(value = "from ConversionHistory history where history.conversionTime BETWEEN :startDate AND :endDate")
    List<ConversionHistory> getAllBetweenDates(@Param("startDate") Date startDate, @Param("endDate")Date endDate);

}

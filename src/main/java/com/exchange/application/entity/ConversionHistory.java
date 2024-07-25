package com.exchange.application.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "CONVERSION_HISTORY")
public class ConversionHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(
            description = "id of the history",
            name = "id",
            type = "int",
            example = "1")
    private Long id;

    @Schema(
            description = "info of the calculation",
            name = "info",
            type = "string",
            example = "EUR to TRY")
    private String conversionInfo;

    @Schema(
            description = "time of the calculation",
            name = "time",
            type = "date",
            example = "2024-07-25")
    private Date conversionTime;

    @Schema(
            description = "result of the calculation",
            name = "result",
            type = "BigDecimal",
            example = "0.01")
    private BigDecimal conversionResult;
}

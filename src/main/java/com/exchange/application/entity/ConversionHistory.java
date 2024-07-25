package com.exchange.application.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "CONVERSION_HISTORY")
public class ConversionHistory implements Serializable {

    private static final long serialVersionUID = 4051575144399637557L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "INFO")
    private String info;

    @Column(name = "TIME")
    private Date time;

    @Column(name = "RESULT")
    private BigDecimal result;
}

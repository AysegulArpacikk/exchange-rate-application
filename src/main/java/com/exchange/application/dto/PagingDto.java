package com.exchange.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PagingDto {

    private Integer pageNo;
    private Integer pageSize;
    private String sortBy;
    private String sortDirection;
}

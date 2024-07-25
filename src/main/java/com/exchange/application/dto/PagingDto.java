package com.exchange.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PagingDto {

    private Integer pageNo;
    private Integer pageSize;
    private String sortBy;
    private String sortDirection;
}

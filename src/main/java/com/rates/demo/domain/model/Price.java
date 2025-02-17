package com.rates.demo.domain.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Price {

    private Long brandId;
    private Integer priceList;
    private Long productId;
    private Integer priority;
    private Double price;
    private String curr;
    private String startDate;
    private String endDate;

}

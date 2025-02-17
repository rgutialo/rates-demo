package com.rates.demo.infrastructure.adapter.in.rest.dto.v1;

public record PriceResponse (String brandId, String productId, String price, String startDate, String endDate, String priceList) {}

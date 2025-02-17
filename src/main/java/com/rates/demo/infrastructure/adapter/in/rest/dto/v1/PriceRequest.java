package com.rates.demo.infrastructure.adapter.in.rest.dto.v1;

import jakarta.validation.constraints.NotNull;

public record PriceRequest (@NotNull String date, @NotNull String productId, @NotNull String brandId) {
}

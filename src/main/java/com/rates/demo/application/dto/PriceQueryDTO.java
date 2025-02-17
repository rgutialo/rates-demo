package com.rates.demo.application.dto;

import java.time.LocalDateTime;

public record PriceQueryDTO(LocalDateTime date, Long productId, Long brandId) {
}

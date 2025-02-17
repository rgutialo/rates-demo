package com.rates.demo.infrastructure.adapter.out.persistence;

import com.rates.demo.application.dto.PriceQueryDTO;
import com.rates.demo.application.port.out.PriceOutputPort;
import com.rates.demo.domain.model.Price;
import com.rates.demo.infrastructure.adapter.out.persistence.entity.PriceEntity;
import com.rates.demo.infrastructure.adapter.out.persistence.mapper.PriceMapper;
import com.rates.demo.infrastructure.adapter.out.persistence.repository.IPriceRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class PriceOutputAdapter implements PriceOutputPort {
    private final IPriceRepo priceRepository;
    private final PriceMapper priceMapper;

    @Override
    public Optional<Price> getPricePerProductIdAndBrandIdAndDate(PriceQueryDTO priceQueryDTO) {
        Optional<PriceEntity> byBrandIdAndProductIdAndDate = priceRepository.findByBrandIdAndProductIdAndDate(priceQueryDTO.brandId(), priceQueryDTO.productId(), priceQueryDTO.date());
        return byBrandIdAndProductIdAndDate.map(priceMapper::entityToDomain);
    }
}

package com.rates.demo.application.port.out;

import com.rates.demo.application.dto.PriceQueryDTO;
import com.rates.demo.domain.model.Price;

import java.util.Optional;

public interface PriceOutputPort {

    Optional<Price> getPricePerProductIdAndBrandIdAndDate(final PriceQueryDTO priceQueryDTO);
}

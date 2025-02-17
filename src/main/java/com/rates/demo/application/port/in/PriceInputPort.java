package com.rates.demo.application.port.in;

import com.rates.demo.application.dto.PriceQueryDTO;
import com.rates.demo.domain.model.Price;

public interface PriceInputPort {
    Price obtainPriceRate(PriceQueryDTO priceQuery);
}

package com.rates.demo.application.service;

import com.rates.demo.application.dto.PriceQueryDTO;
import com.rates.demo.application.port.in.PriceInputPort;
import com.rates.demo.application.port.out.PriceOutputPort;
import com.rates.demo.domain.exception.NotFoundException;
import com.rates.demo.domain.model.Price;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PriceService  implements PriceInputPort {
    private final PriceOutputPort priceOutputPort;

    @Override
    public Price obtainPriceRate(PriceQueryDTO priceQuery) {
    return priceOutputPort.getPricePerProductIdAndBrandIdAndDate(priceQuery).orElseThrow(() ->
            new NotFoundException("Price rate not found for date: " + priceQuery.date() + ", productId: " + priceQuery.productId() + ", brandId: " + priceQuery.brandId()));
    }
}

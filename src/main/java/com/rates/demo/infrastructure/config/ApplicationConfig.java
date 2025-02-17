package com.rates.demo.infrastructure.config;

import com.rates.demo.application.port.in.PriceInputPort;
import com.rates.demo.application.port.out.PriceOutputPort;
import com.rates.demo.application.service.PriceService;
import com.rates.demo.infrastructure.adapter.out.persistence.PriceOutputAdapter;
import com.rates.demo.infrastructure.adapter.out.persistence.mapper.PriceMapper;
import com.rates.demo.infrastructure.adapter.out.persistence.repository.IPriceRepo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Bean
    public PriceOutputPort priceOutputPort(final IPriceRepo priceRepository, final PriceMapper priceMapper) {
        return new PriceOutputAdapter(priceRepository, priceMapper);
    }

    @Bean
    public PriceInputPort priceInputPort(final PriceOutputPort priceOutputPort) {
        return new PriceService(priceOutputPort);
    }
}

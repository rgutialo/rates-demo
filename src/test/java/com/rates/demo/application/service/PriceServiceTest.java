package com.rates.demo.application.service;

import com.rates.demo.application.dto.PriceQueryDTO;
import com.rates.demo.application.port.out.PriceOutputPort;
import com.rates.demo.domain.exception.NotFoundException;
import com.rates.demo.domain.model.Price;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PriceServiceTest {

    @Mock
    private PriceOutputPort priceOutputPort;

    @InjectMocks
    private PriceService priceService;

    @Mock
    private Price price;

    @Mock
    private PriceQueryDTO priceQueryDTO;

    @Test
    void shouldReturnPriceWhenValidQueryIsProvided() {
        when(priceOutputPort.getPricePerProductIdAndBrandIdAndDate(priceQueryDTO))
                .thenReturn(Optional.of(price));

        var result = priceService.obtainPriceRate(priceQueryDTO);

        assertNotNull(result);
        assertEquals(price, result);
        verify(priceOutputPort, times(1)).getPricePerProductIdAndBrandIdAndDate(priceQueryDTO);
    }

    @Test
    void shouldThrowNotFoundExceptionWhenNoPriceIsFound() {
        when(priceOutputPort.getPricePerProductIdAndBrandIdAndDate(priceQueryDTO))
                .thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () ->
                priceService.obtainPriceRate(priceQueryDTO));

        verify(priceOutputPort, times(1)).getPricePerProductIdAndBrandIdAndDate(priceQueryDTO);
    }
}

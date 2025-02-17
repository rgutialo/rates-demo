package com.rates.demo.infrastructure.adapter.out.persistence.mapper;

import com.rates.demo.application.dto.PriceQueryDTO;
import com.rates.demo.domain.model.Price;
import com.rates.demo.infrastructure.adapter.in.rest.dto.v1.PriceRequest;
import com.rates.demo.infrastructure.adapter.in.rest.dto.v1.PriceResponse;
import com.rates.demo.infrastructure.adapter.out.persistence.entity.PriceEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PriceMapper {

    @Mapping(target = "date", source = "date", dateFormat = "yyyy-MM-dd HH:mm:ss")
    PriceQueryDTO toQueryDTO(PriceRequest priceRequest);

    @Mapping(target = "startDate", source = "startDate", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @Mapping(target = "endDate", source = "endDate", dateFormat = "yyyy-MM-dd HH:mm:ss")
    PriceResponse domainToResponse(Price priceModel);

    @Mapping(target = "startDate", source = "startDate", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @Mapping(target = "endDate", source = "endDate", dateFormat = "yyyy-MM-dd HH:mm:ss")
    Price entityToDomain(PriceEntity priceEntity);
}

package com.rates.demo.infrastructure.adapter.out.persistence.repository;

import com.rates.demo.domain.model.Price;
import com.rates.demo.infrastructure.adapter.out.persistence.entity.PriceEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface IPriceRepo extends CrudRepository<PriceEntity, Integer> {

    @Query(
            """
            SELECT p FROM PriceEntity p WHERE p.brandId = :brandId
            AND p.productId = :productId AND p.startDate <= :date AND p.endDate >= :date
            ORDER BY p.priority DESC
            LIMIT 1
            """
    )
    Optional<PriceEntity> findByBrandIdAndProductIdAndDate(final @Param("brandId") Long brandId, final @Param("productId") Long productId, final @Param("date") LocalDateTime date);
}

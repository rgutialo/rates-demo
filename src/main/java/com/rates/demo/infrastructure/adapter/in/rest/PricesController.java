package com.rates.demo.infrastructure.adapter.in.rest;

import com.rates.demo.application.port.in.PriceInputPort;
import com.rates.demo.domain.model.Price;
import com.rates.demo.infrastructure.adapter.in.rest.dto.error.ErrorDetail;
import com.rates.demo.infrastructure.adapter.in.rest.dto.v1.PriceRequest;
import com.rates.demo.infrastructure.adapter.in.rest.dto.v1.PriceResponse;
import com.rates.demo.infrastructure.adapter.out.persistence.mapper.PriceMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/prices")
@AllArgsConstructor
@Validated
public class PricesController {
    private final PriceMapper priceMapper;
    private final PriceInputPort priceInputPort;


    @Operation(
            summary = "Search price rate based on input parameters",
            description =
                    "Search price rate based on the date received, the productId and the brandId")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully operation executed",
                            content =
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = PriceResponse.class))),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Price rate not found",
                            content =
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorDetail.class)))
            })
    @Parameter(name = "date", in = ParameterIn.QUERY)
    @Parameter(name = "productId", in = ParameterIn.QUERY)
    @Parameter(name = "brandId", in = ParameterIn.QUERY)

    @GetMapping
    public ResponseEntity<PriceResponse> obtainPricePerProductAndBrand(@Valid @ModelAttribute PriceRequest priceRequest) {
        final Price price = priceInputPort.obtainPriceRate(priceMapper.toQueryDTO(priceRequest));
        return ResponseEntity.ok().body(priceMapper.domainToResponse(price));
    }
}

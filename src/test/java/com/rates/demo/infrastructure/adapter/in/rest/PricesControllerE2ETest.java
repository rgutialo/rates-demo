package com.rates.demo.infrastructure.adapter.in.rest;

import com.rates.demo.infrastructure.adapter.in.rest.dto.v1.PriceResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class PricesControllerE2ETest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String buildUrl(String queryParams) {
        return "http://localhost:" + port + "/prices" + queryParams;
    }

    @Test
    void shouldReturnPriceList1RowValuesWhenParamsDateIs14062020_10_00AndProduct35455AndBrand1() {
        final String url = buildUrl("?date=2020-06-14 10:00:00&productId=35455&brandId=1");

        final var response = restTemplate.getForEntity(url, PriceResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().productId()).isEqualTo("35455");
        assertThat(response.getBody().brandId()).isEqualTo("1");
        assertThat(response.getBody().priceList()).isEqualTo("1");
        assertThat(response.getBody().price()).isEqualTo("35.5");
        assertThat(response.getBody().startDate()).isEqualTo("2020-06-14 00:00:00");
        assertThat(response.getBody().endDate()).isEqualTo("2020-12-31 23:59:59");
    }

    @Test
    void shouldReturnPriceList2RowValuesWhenParamsDateIs14062020_16_00AndProduct35455AndBrand1() {
        final String url = buildUrl("?date=2020-06-14 16:00:00&productId=35455&brandId=1");

        final var response = restTemplate.getForEntity(url, PriceResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().productId()).isEqualTo("35455");
        assertThat(response.getBody().brandId()).isEqualTo("1");
        assertThat(response.getBody().priceList()).isEqualTo("2");
        assertThat(response.getBody().price()).isEqualTo("25.45");
        assertThat(response.getBody().startDate()).isEqualTo("2020-06-14 15:00:00");
        assertThat(response.getBody().endDate()).isEqualTo("2020-06-14 18:30:00");
    }

    @Test
    void shouldReturnPriceList1RowValuesWhenParamsDateIs14062020_21_00AndProduct35455AndBrand1() {
        final String url = buildUrl("?date=2020-06-14 21:00:00&productId=35455&brandId=1");

        final var response = restTemplate.getForEntity(url, PriceResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().productId()).isEqualTo("35455");
        assertThat(response.getBody().brandId()).isEqualTo("1");
        assertThat(response.getBody().priceList()).isEqualTo("1");
        assertThat(response.getBody().price()).isEqualTo("35.5");
        assertThat(response.getBody().startDate()).isEqualTo("2020-06-14 00:00:00");
        assertThat(response.getBody().endDate()).isEqualTo("2020-12-31 23:59:59");
    }

    @Test
    void shouldReturnPriceList3RowValuesWhenParamsDateIs15062020_10_00AndProduct35455AndBrand1() {
        final String url = buildUrl("?date=2020-06-15 10:00:00&productId=35455&brandId=1");

        final var response = restTemplate.getForEntity(url, PriceResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().productId()).isEqualTo("35455");
        assertThat(response.getBody().brandId()).isEqualTo("1");
        assertThat(response.getBody().priceList()).isEqualTo("3");
        assertThat(response.getBody().price()).isEqualTo("30.5");
        assertThat(response.getBody().startDate()).isEqualTo("2020-06-15 00:00:00");
        assertThat(response.getBody().endDate()).isEqualTo("2020-06-15 11:00:00");
    }

    @Test
    void shouldReturnPriceList4RowValuesWhenParamsDateIs16062020_21_00AndProduct35455AndBrand1() {
        final String url = buildUrl("?date=2020-06-16 21:00:00&productId=35455&brandId=1");

        final var response = restTemplate.getForEntity(url, PriceResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().productId()).isEqualTo("35455");
        assertThat(response.getBody().brandId()).isEqualTo("1");
        assertThat(response.getBody().priceList()).isEqualTo("4");
        assertThat(response.getBody().price()).isEqualTo("38.95");
        assertThat(response.getBody().startDate()).isEqualTo("2020-06-15 16:00:00");
        assertThat(response.getBody().endDate()).isEqualTo("2020-12-31 23:59:59");
    }

    @Test
    void shouldReturnNotFoundWhenPriceDoesNotExist() {
        final String url = buildUrl("?date=2030-06-14 10:00:00&productId=99999&brandId=1");

        final var response = restTemplate.getForEntity(url, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).contains("Price rate not found");
    }
}

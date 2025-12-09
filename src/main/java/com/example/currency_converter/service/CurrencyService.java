package com.example.currency_converter.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.currency_converter.dto.ExchangeRateApiResponse;
import com.example.currency_converter.model.ConversionRecord;
import com.example.currency_converter.repository.ConversionRecordRepository;

@Service
public class CurrencyService {

    private static final Logger log = LoggerFactory.getLogger(CurrencyService.class);

    private final RestTemplate restTemplate;
    private final ConversionRecordRepository repository;

    @Value("${app.exchange-rate-api.base-url}")
    private String baseUrl;

    @Value("${app.exchange-rate-api.key}")
    private String apiKey;

    public CurrencyService(RestTemplate restTemplate, ConversionRecordRepository repository) {
        this.restTemplate = restTemplate;
        this.repository = repository;
    }

    public ConversionRecord convert(String username,
                                    String fromCurrency,
                                    String toCurrency,
                                    BigDecimal amount) {

        ExchangeRateApiResponse apiResponse = fetchRates(fromCurrency);

        Map<String, BigDecimal> rates = apiResponse.getConversion_rates();
        if (rates == null || !rates.containsKey(toCurrency)) {
            throw new IllegalArgumentException("Unsupported target currency: " + toCurrency);
        }

        BigDecimal rate = rates.get(toCurrency);
        BigDecimal resultAmount = amount.multiply(rate);

        ConversionRecord record = new ConversionRecord(
                username,
                fromCurrency,
                toCurrency,
                amount,
                rate,
                resultAmount,
                LocalDateTime.now()
        );

        return repository.save(record);
    }

    public ExchangeRateApiResponse fetchRates(String baseCurrency) {
        String url = String.format("%s/%s/latest/%s", baseUrl, apiKey, baseCurrency);
        log.debug("Calling ExchangeRate-API: {}", url);
        ExchangeRateApiResponse response =
                restTemplate.getForObject(url, ExchangeRateApiResponse.class);

        if (response == null || !"success".equalsIgnoreCase(response.getResult())) {
            throw new IllegalStateException("Failed to fetch exchange rates");
        }

        return response;
    }
}

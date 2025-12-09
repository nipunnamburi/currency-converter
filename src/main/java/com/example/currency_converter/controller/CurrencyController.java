package com.example.currency_converter.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.currency_converter.dto.ConversionRequest;
import com.example.currency_converter.dto.ConversionResponse;
import com.example.currency_converter.dto.ExchangeRateApiResponse;
import com.example.currency_converter.model.ConversionRecord;
import com.example.currency_converter.repository.ConversionRecordRepository;
import com.example.currency_converter.service.CurrencyService;

@RestController
@RequestMapping("/api")
public class CurrencyController {

    private final CurrencyService currencyService;
    private final ConversionRecordRepository repository;

    public CurrencyController(CurrencyService currencyService, ConversionRecordRepository repository) {
        this.currencyService = currencyService;
        this.repository = repository;
    }

    // POST /api/convert
    @PostMapping("/convert")
    public ConversionResponse convert(@RequestBody ConversionRequest request,
                                      Authentication authentication) {

        String username = authentication.getName();

        ConversionRecord record = currencyService.convert(
                username,
                request.getFromCurrency(),
                request.getToCurrency(),
                request.getAmount()
        );

        return new ConversionResponse(
                record.getFromCurrency(),
                record.getToCurrency(),
                record.getAmount(),
                record.getRateUsed(),
                record.getResultAmount(),
                record.getCreatedAt()
        );
    }

    // GET /api/history
    @GetMapping("/history")
    public List<ConversionResponse> getHistory(Authentication authentication) {
        String username = authentication.getName();

        return repository.findByUsernameOrderByCreatedAtDesc(username)
                .stream()
                .map(record -> new ConversionResponse(
                        record.getFromCurrency(),
                        record.getToCurrency(),
                        record.getAmount(),
                        record.getRateUsed(),
                        record.getResultAmount(),
                        record.getCreatedAt()
                ))
                .collect(Collectors.toList());
    }

    // GET /api/rates/{base}
    @GetMapping("/rates/{base}")
    public ExchangeRateApiResponse getRates(@PathVariable("base") String baseCurrency) {
        return currencyService.fetchRates(baseCurrency);
    }
}

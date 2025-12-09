package com.example.currency_converter.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ConversionResponse {

    private String fromCurrency;
    private String toCurrency;
    private BigDecimal amount;
    private BigDecimal rateUsed;
    private BigDecimal resultAmount;
    private LocalDateTime timestamp;

    public ConversionResponse() {}

    public ConversionResponse(String fromCurrency, String toCurrency, BigDecimal amount,
                              BigDecimal rateUsed, BigDecimal resultAmount,
                              LocalDateTime timestamp) {
        this.fromCurrency = fromCurrency;
        this.toCurrency = toCurrency;
        this.amount = amount;
        this.rateUsed = rateUsed;
        this.resultAmount = resultAmount;
        this.timestamp = timestamp;
    }

    public String getFromCurrency() {
        return fromCurrency;
    }

    public void setFromCurrency(String fromCurrency) {
        this.fromCurrency = fromCurrency;
    }

    public String getToCurrency() {
        return toCurrency;
    }

    public void setToCurrency(String toCurrency) {
        this.toCurrency = toCurrency;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getRateUsed() {
        return rateUsed;
    }

    public void setRateUsed(BigDecimal rateUsed) {
        this.rateUsed = rateUsed;
    }

    public BigDecimal getResultAmount() {
        return resultAmount;
    }

    public void setResultAmount(BigDecimal resultAmount) {
        this.resultAmount = resultAmount;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}

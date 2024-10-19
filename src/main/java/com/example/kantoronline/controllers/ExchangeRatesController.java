package com.example.kantoronline.controllers;

import com.example.kantoronline.apiResponse.ExchangeRate;
import com.example.kantoronline.apiResponse.ExchangeRatesTable;
import com.example.kantoronline.enums.CurrencyCode;
import com.example.kantoronline.services.exchangeRate.ExchangeRatesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/exchangerates")
public class ExchangeRatesController {

    private final ExchangeRatesService exchangeRatesService;

    @GetMapping
    public ResponseEntity<ExchangeRatesTable[]> getExchangeRates() {
        ExchangeRatesTable[] response = exchangeRatesService.getExchangeRates();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/archival")
    public ResponseEntity<ExchangeRatesTable[]> getArchivalExchangeRates(@RequestParam LocalDate startDate,
                                                                         @RequestParam LocalDate endDate) {
        ExchangeRatesTable[] response = exchangeRatesService.getArchivalExchangeRates(startDate, endDate);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/currency")
    public ResponseEntity<ExchangeRate> getCurrencyExchangeRate(@RequestParam CurrencyCode currencyCode) {
        ExchangeRate currencyExchangeRate = exchangeRatesService.getCurrencyExchangeRate(currencyCode);
        return ResponseEntity.ok(currencyExchangeRate);
    }
}

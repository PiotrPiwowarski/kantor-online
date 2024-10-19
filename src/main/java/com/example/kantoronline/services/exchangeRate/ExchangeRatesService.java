package com.example.kantoronline.services.exchangeRate;

import com.example.kantoronline.apiResponse.ExchangeRate;
import com.example.kantoronline.apiResponse.ExchangeRatesTable;
import com.example.kantoronline.enums.CurrencyCode;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public interface ExchangeRatesService {
    ExchangeRatesTable[] getExchangeRates();
    ExchangeRatesTable[] getArchivalExchangeRates(LocalDate startDate, LocalDate endDate);
    ExchangeRate getCurrencyExchangeRate(CurrencyCode currencyCode);
}

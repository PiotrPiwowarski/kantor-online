package com.example.kantoronline.services.exchangeRate.impl;

import com.example.kantoronline.apiResponse.ExchangeRate;
import com.example.kantoronline.apiResponse.ExchangeRatesTable;
import com.example.kantoronline.enums.CurrencyCode;
import com.example.kantoronline.exceptions.EndDateIsBeforeStartDateException;
import com.example.kantoronline.services.exchangeRate.ExchangeRatesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class ExchangeRatesServiceImpl implements ExchangeRatesService {


    @Override
    public ExchangeRatesTable[] getExchangeRates() {
        WebClient webClient = WebClient.builder()
                .baseUrl("https://api.nbp.pl")
                .build();

        return webClient.get()
                .uri("/api/exchangerates/tables/C?format=json")
                .retrieve()
                .bodyToMono(ExchangeRatesTable[].class)
                .block();
    }

    @Override
    public ExchangeRatesTable[] getArchivalExchangeRates(LocalDate startDate, LocalDate endDate) {
        if(endDate.isBefore(startDate)) {
            throw new EndDateIsBeforeStartDateException();
        }
        WebClient webClient = WebClient.builder()
                .baseUrl("https://api.nbp.pl")
                .build();

        return webClient.get()
                .uri("/api/exchangerates/tables/C/" + startDate.toString() + "/" + endDate.toString() + "?format=json")
                .retrieve()
                .bodyToMono(ExchangeRatesTable[].class)
                .block();
    }

    @Override
    public ExchangeRate getCurrencyExchangeRate(CurrencyCode currencyCode) {
        WebClient webClient = WebClient.builder()
                .baseUrl("https://api.nbp.pl")
                .build();

        return webClient.get()
                .uri("/api/exchangerates/rates/C/USD?format=json")
                .retrieve()
                .bodyToMono(ExchangeRate.class)
                .block();
    }
}

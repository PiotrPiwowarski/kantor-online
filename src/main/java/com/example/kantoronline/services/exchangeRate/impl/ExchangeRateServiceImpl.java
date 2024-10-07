package com.example.kantoronline.services.exchangeRate.impl;

import com.example.kantoronline.services.exchangeRate.ExchangeRateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.net.http.HttpClient;

@Service
@RequiredArgsConstructor
public class ExchangeRateServiceImpl implements ExchangeRateService {

    private final static String BUYING_AND_SELLING_RATE_URL = "https://api.nbp.pl/api/exchangerates/tables/C/?format=json";

    private final HttpClient httpClient = HttpClient.newHttpClient();
}

package com.example.kantoronline.services.curency;

import com.example.kantoronline.dtos.AccountBalanceDto;
import com.example.kantoronline.dtos.AddCurrencyDto;
import org.springframework.stereotype.Service;

@Service
public interface CurrencyService {

    void addCurrency(AddCurrencyDto addCurrencyDto);
    AccountBalanceDto getAccountBalanceDto(long accountId, String currencyCode);
}

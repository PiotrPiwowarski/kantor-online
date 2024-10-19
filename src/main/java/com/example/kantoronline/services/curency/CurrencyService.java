package com.example.kantoronline.services.curency;

import com.example.kantoronline.dtos.AccountBalanceDto;
import com.example.kantoronline.dtos.AddCurrencyDto;
import com.example.kantoronline.dtos.CurrencyPurchaseDto;
import com.example.kantoronline.dtos.SellCurrencyDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CurrencyService {

    void deposit(AddCurrencyDto addCurrencyDto);
    AccountBalanceDto getAccountBalanceBySpecificCurrency(long accountId, String currencyCode);
    List<AccountBalanceDto> getAccountBalance(long accountId);
    void cashOut(SellCurrencyDto sellCurrencyDto);
    void currencyPurchase(CurrencyPurchaseDto currencyPurchaseDto);
}

package com.example.kantoronline.services.curency;

import com.example.kantoronline.dtos.AccountBalanceDto;
import com.example.kantoronline.dtos.AddCurrencyDto;
import com.example.kantoronline.dtos.CurrencyPurchaseDto;
import com.example.kantoronline.dtos.CurrencyWithdrawalDto;
import com.example.kantoronline.enums.CurrencyCode;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CurrencyService {

    void deposit(AddCurrencyDto addCurrencyDto);
    AccountBalanceDto getAccountBalanceBySpecificCurrency(long accountId, CurrencyCode currencyCode);
    List<AccountBalanceDto> getAccountBalance(long accountId);
    void withdrawal(CurrencyWithdrawalDto currencyWithdrawalDto);
    void currencyPurchase(CurrencyPurchaseDto currencyPurchaseDto);
}

package com.example.kantoronline.services.curency.impl;

import com.example.kantoronline.dtos.AccountBalanceDto;
import com.example.kantoronline.dtos.AddCurrencyDto;
import com.example.kantoronline.entities.Account;
import com.example.kantoronline.entities.Currency;
import com.example.kantoronline.enums.CurrencyCode;
import com.example.kantoronline.exceptions.NoAccountWithSuchIdException;
import com.example.kantoronline.exceptions.WrongCurrencyCodeException;
import com.example.kantoronline.repositories.AccountRepository;
import com.example.kantoronline.repositories.CurrencyRepository;
import com.example.kantoronline.services.curency.CurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CurrencyServiceImpl implements CurrencyService {

    private final CurrencyRepository currencyRepository;
    private final AccountRepository accountRepository;

    private Account getAccount(long accountId) {
        Optional<Account> optionalAccount = accountRepository.findById(accountId);
        if(optionalAccount.isEmpty()) {
            throw new NoAccountWithSuchIdException();
        }
        return optionalAccount.get();
    }

    @Override
    public void addCurrency(AddCurrencyDto addCurrencyDto) {
        Account account = getAccount(addCurrencyDto.getAccountId());
        Optional<Currency> optionalCurrency = currencyRepository.findByAccountAndCurrencyCode(account, addCurrencyDto.getCurrencyCode());
        Currency currency;
        if(optionalCurrency.isEmpty()) {
            currency = Currency.builder()
                    .currencyValue(addCurrencyDto.getCurrencyValue())
                    .currencyCode(addCurrencyDto.getCurrencyCode())
                    .account(account)
                    .build();

        } else {
            currency = optionalCurrency.get();
            BigDecimal value = currency.getCurrencyValue();
            BigDecimal sum = value.add(addCurrencyDto.getCurrencyValue());
            currency.setCurrencyValue(sum);
        }
        currencyRepository.save(currency);
    }

    @Override
    public AccountBalanceDto getAccountBalanceDto(long accountId, String currencyCode) {
        Account account = getAccount(accountId);
        CurrencyCode code;
        try {
            code = CurrencyCode.valueOf(currencyCode);
        } catch (Exception e) {
            throw new WrongCurrencyCodeException();
        }
        Optional<Currency> optionalCurrency = currencyRepository
                .findByAccountAndCurrencyCode(account, code);
        if(optionalCurrency.isEmpty()) {
            return AccountBalanceDto.builder()
                    .currencyCode(code)
                    .email(account.getEmail())
                    .accountId(account.getId())
                    .currencyValue(BigDecimal.ZERO)
                    .build();
        } else {
            Currency currency = optionalCurrency.get();
            return AccountBalanceDto.builder()
                    .currencyCode(code)
                    .email(account.getEmail())
                    .accountId(account.getId())
                    .currencyValue(currency.getCurrencyValue())
                    .build();
        }
    }
}

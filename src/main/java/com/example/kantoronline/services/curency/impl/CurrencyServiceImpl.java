package com.example.kantoronline.services.curency.impl;

import com.example.kantoronline.dtos.AccountBalanceDto;
import com.example.kantoronline.dtos.AddCurrencyDto;
import com.example.kantoronline.dtos.SellCurrencyDto;
import com.example.kantoronline.entities.Account;
import com.example.kantoronline.entities.Currency;
import com.example.kantoronline.enums.CurrencyCode;
import com.example.kantoronline.enums.TransactionType;
import com.example.kantoronline.exceptions.NoAccountWithSuchIdException;
import com.example.kantoronline.exceptions.NotEnoughCurrencyToMakeTransaction;
import com.example.kantoronline.exceptions.WrongCurrencyCodeException;
import com.example.kantoronline.repositories.AccountRepository;
import com.example.kantoronline.repositories.CurrencyRepository;
import com.example.kantoronline.repositories.TransactionRepository;
import com.example.kantoronline.services.account.AccountService;
import com.example.kantoronline.services.curency.CurrencyService;
import com.example.kantoronline.services.transaction.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CurrencyServiceImpl implements CurrencyService {

    private final CurrencyRepository currencyRepository;
    private final AccountService accountService;
    private final TransactionService transactionService;

    @Override
    public void addCurrency(AddCurrencyDto addCurrencyDto) {
        Account account = accountService.getAccount(addCurrencyDto.getAccountId());
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
        transactionService.addTransaction(TransactionType.TOPPING_UP_ACCOUNT, addCurrencyDto.getCurrencyValue(), addCurrencyDto.getCurrencyCode(), addCurrencyDto.getAccountId());
        currencyRepository.save(currency);
    }

    @Override
    public void sellCurrency(SellCurrencyDto sellCurrencyDto) {
        Account account = accountService.getAccount(sellCurrencyDto.getAccountId());
        Optional<Currency> optionalCurrency = currencyRepository.findByAccountAndCurrencyCode(account, sellCurrencyDto.getCurrencyCode());
        Currency currency = optionalCurrency.orElseThrow(NotEnoughCurrencyToMakeTransaction::new);
        BigDecimal result = currency.getCurrencyValue().subtract(sellCurrencyDto.getCurrencyValue());
        if(result.compareTo(BigDecimal.ZERO) < 0) {
            throw new NotEnoughCurrencyToMakeTransaction();
        }
        currency.setCurrencyValue(result);
        currencyRepository.save(currency);
    }

    @Override
    public AccountBalanceDto getAccountBalanceBySpecificCurrency(long accountId, String currencyCode) {
        Account account = accountService.getAccount(accountId);
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

    @Override
    public List<AccountBalanceDto> getAccountBalance(long accountId) {
        Account account = accountService.getAccount(accountId);
        List<Currency> currencies = currencyRepository.findAllByAccount(account);
        return currencies.stream().map(currency -> AccountBalanceDto.builder()
                .currencyCode(currency.getCurrencyCode())
                .email(account.getEmail())
                .accountId(account.getId())
                .currencyValue(currency.getCurrencyValue())
                .build()).toList();
    }
}

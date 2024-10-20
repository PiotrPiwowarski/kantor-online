package com.example.kantoronline.services.curency.impl;

import com.example.kantoronline.dtos.AccountBalanceDto;
import com.example.kantoronline.dtos.AddCurrencyDto;
import com.example.kantoronline.dtos.CurrencyPurchaseDto;
import com.example.kantoronline.dtos.CurrencyWithdrawalDto;
import com.example.kantoronline.entities.Account;
import com.example.kantoronline.entities.Currency;
import com.example.kantoronline.enums.CurrencyCode;
import com.example.kantoronline.enums.TransactionType;
import com.example.kantoronline.exceptions.BothCurrenciesAreTheSameException;
import com.example.kantoronline.exceptions.NoPlnInTransactionException;
import com.example.kantoronline.exceptions.NotEnoughCurrencyToMakeTransactionException;
import com.example.kantoronline.repositories.CurrencyRepository;
import com.example.kantoronline.services.account.AccountService;
import com.example.kantoronline.services.curency.CurrencyService;
import com.example.kantoronline.services.exchangeRate.ExchangeRatesService;
import com.example.kantoronline.services.transaction.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CurrencyServiceImpl implements CurrencyService {

    private final CurrencyRepository currencyRepository;
    private final AccountService accountService;
    private final TransactionService transactionService;
    private final ExchangeRatesService exchangeRatesService;

    private final static BigDecimal AFTER_DEDUCTING_COMMISSION = new BigDecimal("0.92");

    @Override
    public void deposit(AddCurrencyDto addCurrencyDto) {
        Account account = accountService.getAccount(addCurrencyDto.getAccountId());
        Optional<Currency> optionalCurrency = currencyRepository.findByAccountAndCurrencyCode(account, addCurrencyDto.getCurrencyCode());
        Currency currency;
        if(optionalCurrency.isEmpty()) {
            currency = Currency.builder()
                    .currencyValue(addCurrencyDto.getCurrencyValue().multiply(AFTER_DEDUCTING_COMMISSION))
                    .currencyCode(addCurrencyDto.getCurrencyCode())
                    .account(account)
                    .build();

        } else {
            currency = optionalCurrency.get();
            BigDecimal value = currency.getCurrencyValue();
            BigDecimal sum = value.add(addCurrencyDto.getCurrencyValue()).multiply(AFTER_DEDUCTING_COMMISSION);
            currency.setCurrencyValue(sum);
        }
        transactionService.addTransaction(TransactionType.DEPOSIT, addCurrencyDto.getCurrencyValue(),
                addCurrencyDto.getCurrencyCode(), addCurrencyDto.getAccountId());
        currencyRepository.save(currency);
    }

    @Override
    public void withdrawal(CurrencyWithdrawalDto currencyWithdrawalDto) {
        Account account = accountService.getAccount(currencyWithdrawalDto.getAccountId());
        Optional<Currency> optionalCurrency = currencyRepository.findByAccountAndCurrencyCode(account, currencyWithdrawalDto.getCurrencyCode());
        Currency currency = optionalCurrency.orElseThrow(NotEnoughCurrencyToMakeTransactionException::new);
        BigDecimal result = currency.getCurrencyValue().subtract(currencyWithdrawalDto.getCurrencyValue()).multiply(AFTER_DEDUCTING_COMMISSION);
        if(result.compareTo(BigDecimal.ZERO) < 0) {
            throw new NotEnoughCurrencyToMakeTransactionException();
        }
        currency.setCurrencyValue(result);
        transactionService.addTransaction(TransactionType.WITHDRAWAL, currencyWithdrawalDto.getCurrencyValue(),
                currencyWithdrawalDto.getCurrencyCode(), currencyWithdrawalDto.getAccountId());
        currencyRepository.save(currency);
    }

    @Override
    @Transactional
    public void currencyPurchase(CurrencyPurchaseDto currencyPurchaseDto) {
        Account account = accountService.getAccount(currencyPurchaseDto.getAccountId());
        List<Currency> userCurrencies = account.getCurrencies();
        List<Currency> fromCurrencyList = userCurrencies.stream().filter(c -> c.getCurrencyCode()
                .equals(currencyPurchaseDto.getFromCurrency())).toList();

        checkIfEnoughCurrencyToMakeATransaction(fromCurrencyList, currencyPurchaseDto);

        Currency fromCurrency = fromCurrencyList.get(0);
        BigDecimal valueAfterTransactionFromCurrency = fromCurrency.getCurrencyValue().subtract(currencyPurchaseDto.getCurrencyValue());
        fromCurrency.setCurrencyValue(valueAfterTransactionFromCurrency);

        List<Currency> toCurrencyList = userCurrencies.stream().filter(c -> c.getCurrencyCode().equals(currencyPurchaseDto.getToCurrency())).toList();
        Currency toCurrency;

        BigDecimal divisor;
        BigDecimal purchasedCurrency;
        TransactionType type;

        if(currencyPurchaseDto.getFromCurrency().equals(CurrencyCode.PLN)) {
            divisor = exchangeRatesService.getCurrencyExchangeRate(currencyPurchaseDto.getToCurrency()).getRates().get(0).getAsk();
            purchasedCurrency = currencyPurchaseDto.getCurrencyValue().divide(divisor, 2, RoundingMode.HALF_UP);
            type = TransactionType.PURCHASE;
        } else if(currencyPurchaseDto.getToCurrency().equals(CurrencyCode.PLN)) {
            divisor = exchangeRatesService.getCurrencyExchangeRate(currencyPurchaseDto.getFromCurrency()).getRates().get(0).getBid();
            purchasedCurrency = currencyPurchaseDto.getCurrencyValue().multiply(divisor);
            type = TransactionType.SALE;
        } else {
            throw new NoPlnInTransactionException();
        }

        if(toCurrencyList.isEmpty()) {
            toCurrency = Currency.builder()
                    .currencyCode(currencyPurchaseDto.getToCurrency())
                    .account(account)
                    .currencyValue(purchasedCurrency.multiply(AFTER_DEDUCTING_COMMISSION))
                    .build();
            userCurrencies.add(toCurrency);
        } else {
            toCurrency = toCurrencyList.get(0);
            toCurrency.setCurrencyValue(toCurrency.getCurrencyValue().add(purchasedCurrency).multiply(AFTER_DEDUCTING_COMMISSION));
            List<Currency> list = userCurrencies.stream().filter(c -> !c.getCurrencyCode().equals(currencyPurchaseDto.getToCurrency())).toList();
            userCurrencies.addAll(list);
            userCurrencies.add(toCurrency);
        }
        transactionService.addTransaction(type, purchasedCurrency, currencyPurchaseDto.getToCurrency(), account.getId());
    }

    private void checkIfEnoughCurrencyToMakeATransaction(List<Currency> fromCurrency, CurrencyPurchaseDto currencyPurchaseDto) {
        if(fromCurrency.isEmpty() || fromCurrency.get(0).getCurrencyValue().compareTo(currencyPurchaseDto.getCurrencyValue()) < 0) {
            throw new NotEnoughCurrencyToMakeTransactionException();
        }
        if(currencyPurchaseDto.getFromCurrency().equals(currencyPurchaseDto.getToCurrency())) {
            throw new BothCurrenciesAreTheSameException();
        }
    }

    @Override
    public AccountBalanceDto getAccountBalanceBySpecificCurrency(long accountId, CurrencyCode currencyCode) {
        Account account = accountService.getAccount(accountId);
        Optional<Currency> optionalCurrency = currencyRepository
                .findByAccountAndCurrencyCode(account, currencyCode);
        if(optionalCurrency.isEmpty()) {
            return AccountBalanceDto.builder()
                    .currencyCode(currencyCode)
                    .email(account.getEmail())
                    .accountId(account.getId())
                    .currencyValue(BigDecimal.ZERO)
                    .build();
        } else {
            Currency currency = optionalCurrency.get();
            return AccountBalanceDto.builder()
                    .currencyCode(currencyCode)
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

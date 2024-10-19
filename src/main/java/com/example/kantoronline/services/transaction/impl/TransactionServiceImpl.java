package com.example.kantoronline.services.transaction.impl;

import com.example.kantoronline.entities.Account;
import com.example.kantoronline.entities.Transaction;
import com.example.kantoronline.enums.CurrencyCode;
import com.example.kantoronline.enums.TransactionType;
import com.example.kantoronline.repositories.TransactionRepository;
import com.example.kantoronline.services.account.AccountService;
import com.example.kantoronline.services.transaction.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountService accountService;

    @Override
    public void addTransaction(TransactionType transactionType, BigDecimal currencyValue, CurrencyCode currencyCode, long accountId) {
        Account account = accountService.getAccount(accountId);
        Transaction transaction = Transaction.builder()
                .transactionType(transactionType)
                .currencyValue(currencyValue)
                .currencyCode(currencyCode)
                .account(account)
                .build();
        transactionRepository.save(transaction);
    }
}

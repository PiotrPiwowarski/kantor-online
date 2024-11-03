package com.example.kantoronline.services.transaction.impl;

import com.example.kantoronline.dtos.GetTransactionDto;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

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
                .dateTime(LocalDateTime.now())
                .build();
        transactionRepository.save(transaction);
    }

    @Override
    public List<GetTransactionDto> getAllTransactions(long accountId) {
        Account account = accountService.getAccount(accountId);
        List<Transaction> transactions = transactionRepository.findAllByAccount(account);
        return transactions.stream()
                .map(transaction -> GetTransactionDto.builder()
                        .id(transaction.getId())
                        .transactionType(transaction.getTransactionType())
                        .currencyValue(transaction.getCurrencyValue())
                        .currencyCode(transaction.getCurrencyCode())
                        .accountId(account.getId())
                        .dateTime(transaction.getDateTime())
                        .build())
                .toList();
    }

    @Override
    public List<GetTransactionDto> getTransactionsOfDate(long accountId, LocalDate date) {
        Account account = accountService.getAccount(accountId);
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(23, 59, 59);
        List<Transaction> transactions = transactionRepository.findAllByAccountAndDateTimeBetween(account, startOfDay, endOfDay);
        return transactions.stream()
                .map(transaction -> GetTransactionDto.builder()
                        .id(transaction.getId())
                        .transactionType(transaction.getTransactionType())
                        .currencyValue(transaction.getCurrencyValue())
                        .currencyCode(transaction.getCurrencyCode())
                        .accountId(account.getId())
                        .dateTime(transaction.getDateTime())
                        .build())
                .toList();
    }
}

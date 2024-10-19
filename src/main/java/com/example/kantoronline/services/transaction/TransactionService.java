package com.example.kantoronline.services.transaction;

import com.example.kantoronline.dtos.GetTransactionDto;
import com.example.kantoronline.entities.Transaction;
import com.example.kantoronline.enums.CurrencyCode;
import com.example.kantoronline.enums.TransactionType;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public interface TransactionService {

    void addTransaction(TransactionType transactionType, BigDecimal currencyValue, CurrencyCode currencyCode, long accountId);
    List<GetTransactionDto> getAllTransactions(long accountId);
    List<GetTransactionDto> getTransactionsOfDate(long accountId, int year, int month, int day);

}

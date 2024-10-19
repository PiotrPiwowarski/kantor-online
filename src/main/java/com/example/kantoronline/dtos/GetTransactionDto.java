package com.example.kantoronline.dtos;

import com.example.kantoronline.enums.CurrencyCode;
import com.example.kantoronline.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class GetTransactionDto {

    private long id;
    private TransactionType transactionType;
    private BigDecimal currencyValue;
    private CurrencyCode currencyCode;
    private long accountId;
    private LocalDate date;
}

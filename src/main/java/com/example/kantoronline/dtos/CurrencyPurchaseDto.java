package com.example.kantoronline.dtos;

import com.example.kantoronline.enums.CurrencyCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CurrencyPurchaseDto {

    private long accountId;
    private CurrencyCode fromCurrency;
    private CurrencyCode toCurrency;
    private BigDecimal currencyValue;
}

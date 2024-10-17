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
public class AddCurrencyDto {

    private long accountId;
    private BigDecimal currencyValue;
    private CurrencyCode currencyCode;
}
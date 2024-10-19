package com.example.kantoronline.dtos;

import com.example.kantoronline.enums.CurrencyCode;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AccountBalanceDto {

    @NotNull
    private BigDecimal currencyValue;
    @NotNull
    private CurrencyCode currencyCode;
    private long accountId;
    @NotNull
    private String email;
}

package com.example.kantoronline.dtos;

import com.example.kantoronline.enums.CurrencyCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class GetAccountBalanceDto {

    private long accountId;
    private CurrencyCode currencyCode;
}

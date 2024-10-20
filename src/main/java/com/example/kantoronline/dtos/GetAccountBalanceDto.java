package com.example.kantoronline.dtos;

import com.example.kantoronline.enums.CurrencyCode;
import jakarta.validation.constraints.NotNull;
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
    @NotNull
    private CurrencyCode currencyCode;
}

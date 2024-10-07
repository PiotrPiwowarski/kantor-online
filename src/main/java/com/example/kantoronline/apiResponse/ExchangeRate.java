package com.example.kantoronline.apiResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ExchangeRate {

    private String currency;
    private String code;
    private BigDecimal bid;
    private BigDecimal ask;
}

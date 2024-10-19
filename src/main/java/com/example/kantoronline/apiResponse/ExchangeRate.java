package com.example.kantoronline.apiResponse;

import com.example.kantoronline.enums.CurrencyCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ExchangeRate {

    private String table;
    private String currency;
    private CurrencyCode code;
    private List<Rate> rates;

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    @Builder
    public static class Rate {

        private String no;
        private LocalDate effectiveDate;
        private BigDecimal bid;
        private BigDecimal ask;
    }
}

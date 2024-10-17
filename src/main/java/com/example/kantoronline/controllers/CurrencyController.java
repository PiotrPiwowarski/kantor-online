package com.example.kantoronline.controllers;

import com.example.kantoronline.dtos.AddCurrencyDto;
import com.example.kantoronline.dtos.AccountBalanceDto;
import com.example.kantoronline.services.curency.CurrencyService;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/currency")
public class CurrencyController {

    private final CurrencyService currencyService;

    @Parameter(description = "zasilenie konta")
    @PostMapping
    public ResponseEntity<Long> addCurrency(@RequestBody AddCurrencyDto addCurrencyDto) {
        currencyService.addCurrency(addCurrencyDto);
        return ResponseEntity.ok().build();
    }

    @Parameter(description = "pobranie stanu konta w konkretnej walucie")
    @GetMapping("/{accountId}")
    public ResponseEntity<AccountBalanceDto> getAccountBalance(@PathVariable long accountId, @NonNull @RequestParam String currencyCode) {
        AccountBalanceDto accountBalanceDto = currencyService.getAccountBalanceDto(accountId, currencyCode);
        return ResponseEntity.ok(accountBalanceDto);
    }
}

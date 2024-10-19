package com.example.kantoronline.controllers;

import com.example.kantoronline.dtos.AddCurrencyDto;
import com.example.kantoronline.dtos.AccountBalanceDto;
import com.example.kantoronline.dtos.CurrencyPurchaseDto;
import com.example.kantoronline.dtos.SellCurrencyDto;
import com.example.kantoronline.services.curency.CurrencyService;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/currency")
public class CurrencyController {

    private final CurrencyService currencyService;

    @Parameter(description = "zasilenie konta")
    @PostMapping("/deposit")
    public ResponseEntity<Long> deposit(@RequestBody AddCurrencyDto addCurrencyDto) {
        currencyService.deposit(addCurrencyDto);
        return ResponseEntity.ok().build();
    }

    @Parameter(description = "wypłacanie pieniędzy")
    @PostMapping("/cashout")
    public ResponseEntity<Void> cashOut(@RequestBody SellCurrencyDto sellCurrencyDto){
        currencyService.cashOut(sellCurrencyDto);
        return ResponseEntity.ok().build();
    }

    @Parameter(description = "pobranie stanu konta w konkretnej walucie")
    @GetMapping("/{accountId}/specific")
    public ResponseEntity<AccountBalanceDto> getAccountBalanceBySpecificCurrency(@PathVariable long accountId, @RequestParam String currencyCode) {
        AccountBalanceDto accountBalanceDto = currencyService.getAccountBalanceBySpecificCurrency(accountId, currencyCode);
        return ResponseEntity.ok(accountBalanceDto);
    }

    @Parameter(description = "pobranie stanu konta we wszystkich walutach")
    @GetMapping("/{accountId}")
    public ResponseEntity<List<AccountBalanceDto>> getAccountBalance(@PathVariable long accountId) {
        List<AccountBalanceDto> accountBalanceDto = currencyService.getAccountBalance(accountId);
        return ResponseEntity.ok(accountBalanceDto);
    }

    @Parameter(description = "zakup waluty")
    @PostMapping("/currencypurchase")
    public ResponseEntity<Void> currencyPurchase(@RequestBody CurrencyPurchaseDto currencyPurchaseDto) {
        currencyService.currencyPurchase(currencyPurchaseDto);
        return ResponseEntity.ok().build();
    }
}

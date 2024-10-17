package com.example.kantoronline.controllers;

import com.example.kantoronline.dtos.AccountDto;
import com.example.kantoronline.dtos.AddAccountDto;
import com.example.kantoronline.services.account.AccountService;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/account")
public class AccountController {

    private final AccountService accountService;

    @Parameter(description = "dodanie nowego konta")
    @PostMapping
    public ResponseEntity<Long> addAccount(@RequestBody AddAccountDto addAccountDto) {
        long id = accountService.addAccount(addAccountDto);
        return ResponseEntity.ok(id);
    }

    @Parameter(description = "usuwanie konta po id")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable long id) {
        accountService.deleteAccount(id);
        return ResponseEntity.ok().build();
    }

    @Parameter(description = "pobieranie konta po id")
    @GetMapping("/{id}")
    public ResponseEntity<AccountDto> getAccount(@PathVariable long id) {
        AccountDto accountDto = accountService.getAccount(id);
        return ResponseEntity.ok(accountDto);
    }

    @Parameter(description = "jeszcze nie zaimplementowane")
    @PostMapping("/login")
    public ResponseEntity<Void> login() {
        return ResponseEntity.ok().build();
    }

    @Parameter(description = "jeszcze nie zaimplementowane")
    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        return ResponseEntity.ok().build();
    }
}

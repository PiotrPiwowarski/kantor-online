package com.example.kantoronline.controllers;

import com.example.kantoronline.dtos.AccountDto;
import com.example.kantoronline.dtos.AddAccountDto;
import com.example.kantoronline.services.account.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/accounts")
@Tag(name = "Accounts API")
@CrossOrigin(allowedHeaders = "*", origins = "*")
public class AccountController {

    private final AccountService accountService;

    @Operation(summary = "Dodawanie nowego konta")
    @PostMapping
    public ResponseEntity<Long> addAccount(@RequestBody AddAccountDto addAccountDto) {
        long id = accountService.addAccount(addAccountDto);
        return ResponseEntity.ok(id);
    }

    @Operation(summary = "Usuwanie konta")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable long id) {
        accountService.deleteAccount(id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Pobieranie konta")
    @GetMapping("/{id}")
    public ResponseEntity<AccountDto> getAccount(@PathVariable long id) {
        AccountDto accountDto = accountService.getAccountDto(id);
        return ResponseEntity.ok(accountDto);
    }

    @Operation(summary = "Zalogowanie")
    @PostMapping("/login")
    public ResponseEntity<Void> login() {
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Wylogowanie")
    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        return ResponseEntity.ok().build();
    }
}

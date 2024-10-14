package com.example.kantoronline.controllers;

import com.example.kantoronline.dtos.AccountDto;
import com.example.kantoronline.dtos.AddAccountDto;
import com.example.kantoronline.services.account.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/account")
public class AccountController {

    private final AccountService accountService;

    @PostMapping
    public ResponseEntity<Long> addAccount(@RequestBody AddAccountDto addAccountDto) {
        long id = accountService.addAccount(addAccountDto);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable long id) {
        accountService.deleteAccount(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountDto> getAccount(@PathVariable long id) {
        AccountDto accountDto = accountService.getAccount(id);
        return ResponseEntity.ok(accountDto);
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login() {
        return ResponseEntity.ok().build();
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        return ResponseEntity.ok().build();
    }
}

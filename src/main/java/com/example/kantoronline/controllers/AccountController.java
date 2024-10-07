package com.example.kantoronline.controllers;

import com.example.kantoronline.dtos.AddAccountDto;
import com.example.kantoronline.services.AccountService;
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
}

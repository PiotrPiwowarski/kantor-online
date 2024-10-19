package com.example.kantoronline.controllers;

import com.example.kantoronline.dtos.GetTransactionDto;
import com.example.kantoronline.services.transaction.TransactionService;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    @Parameter(description = "pobieranie historii wszystkich transakcji")
    @GetMapping("/{accountId}/all")
    public ResponseEntity<List<GetTransactionDto>> getAllTransactions(@PathVariable long accountId) {
        List<GetTransactionDto> transactions = transactionService.getAllTransactions(accountId);
        return ResponseEntity.ok(transactions);
    }

    @Parameter(description = "pobieranie historii transakcji danego dnia")
    @GetMapping("/{accountId}")
    public ResponseEntity<List<GetTransactionDto>> getTransactionsOfDate(@PathVariable long accountId,
                                                                   @RequestParam int year,
                                                                   @RequestParam int month,
                                                                   @RequestParam int day) {
        List<GetTransactionDto> transactions = transactionService.getTransactionsOfDate(accountId, year, month, day);
        return ResponseEntity.ok(transactions);
    }
}

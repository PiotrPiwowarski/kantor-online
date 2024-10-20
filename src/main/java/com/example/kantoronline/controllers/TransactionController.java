package com.example.kantoronline.controllers;

import com.example.kantoronline.dtos.GetTransactionDto;
import com.example.kantoronline.services.transaction.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/transactions")
@Tag(name = "Transactions API")
@CrossOrigin(allowedHeaders = "*", origins = "*")
public class TransactionController {

    private final TransactionService transactionService;

    @Operation(summary = "Pobieranie listy wszystkich transakcji")
    @GetMapping("/{accountId}/all")
    public ResponseEntity<List<GetTransactionDto>> getAllTransactions(@PathVariable long accountId) {
        List<GetTransactionDto> transactions = transactionService.getAllTransactions(accountId);
        return ResponseEntity.ok(transactions);
    }

    @Operation(summary = "Pobieranie listy transakcji z danego dnia")
    @GetMapping("/{accountId}")
    public ResponseEntity<List<GetTransactionDto>> getTransactionsOfDate(@PathVariable long accountId,
                                                                   @RequestParam int year,
                                                                   @RequestParam int month,
                                                                   @RequestParam int day) {
        List<GetTransactionDto> transactions = transactionService.getTransactionsOfDate(accountId, year, month, day);
        return ResponseEntity.ok(transactions);
    }
}

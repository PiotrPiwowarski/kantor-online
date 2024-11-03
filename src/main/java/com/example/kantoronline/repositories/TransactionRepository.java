package com.example.kantoronline.repositories;

import com.example.kantoronline.entities.Account;
import com.example.kantoronline.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findAllByAccount(Account account);
    List<Transaction> findAllByAccountAndDateTimeBetween(Account account, LocalDateTime start, LocalDateTime end);
}

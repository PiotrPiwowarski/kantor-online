package com.example.kantoronline.repositories;

import com.example.kantoronline.entities.Account;
import com.example.kantoronline.entities.Currency;
import com.example.kantoronline.enums.CurrencyCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, Long> {
    Optional<Currency> findByAccountAndCurrencyCode(Account account, CurrencyCode currencyCode);
    List<Currency> findAllByAccount(Account account);
}

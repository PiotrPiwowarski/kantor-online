package com.example.kantoronline.repositories;

import com.example.kantoronline.entities.Account;
import com.example.kantoronline.entities.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {

    Optional<Token> findByToken(String token);

    @Transactional
    @Modifying
    void deleteAllByAccount(Account account);
}

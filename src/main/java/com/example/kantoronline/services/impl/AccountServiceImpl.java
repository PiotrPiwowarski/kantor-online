package com.example.kantoronline.services.impl;

import com.example.kantoronline.dtos.AddAccountDto;
import com.example.kantoronline.entities.Account;
import com.example.kantoronline.mapper.AccountMapper;
import com.example.kantoronline.repositories.AccountRepository;
import com.example.kantoronline.services.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    @Override
    public long addAccount(AddAccountDto addAccountDto) {
        Account account = AccountMapper.map(addAccountDto);
        Account savedAccount = accountRepository.save(account);
        return savedAccount.getId();
    }
}

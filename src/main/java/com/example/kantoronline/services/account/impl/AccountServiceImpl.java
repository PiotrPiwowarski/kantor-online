package com.example.kantoronline.services.account.impl;

import com.example.kantoronline.dtos.AddAccountDto;
import com.example.kantoronline.entities.Account;
import com.example.kantoronline.exceptions.AccountWithSuchEmailAlreadyExistsException;
import com.example.kantoronline.mapper.AccountMapper;
import com.example.kantoronline.repositories.AccountRepository;
import com.example.kantoronline.services.account.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    @Override
    public long addAccount(AddAccountDto addAccountDto) {
        Optional<Account> optionalAccount = accountRepository.findByEmail(addAccountDto.getEmail());
        if(optionalAccount.isPresent()) {
            throw new AccountWithSuchEmailAlreadyExistsException();
        }
        Account account = AccountMapper.map(addAccountDto);
        Account savedAccount = accountRepository.save(account);
        return savedAccount.getId();
    }

    @Override
    public void deleteAccount(long id) {
        accountRepository.deleteById(id);
    }
}

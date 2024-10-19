package com.example.kantoronline.mapper;

import com.example.kantoronline.dtos.AccountDto;
import com.example.kantoronline.dtos.AddAccountDto;
import com.example.kantoronline.entities.Account;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AccountMapper {

    public static Account map(AddAccountDto addAccountDto) {
        return Account.builder()
                .firstName(addAccountDto.getFirstName())
                .lastName(addAccountDto.getLastName())
                .email(addAccountDto.getEmail())
                .password(addAccountDto.getPassword())
                .build();
    }

    public static AccountDto map(Account account) {
        return AccountDto.builder()
                .id(account.getId())
                .firstName(account.getFirstName())
                .lastName(account.getLastName())
                .email(account.getEmail())
                .build();
    }
}

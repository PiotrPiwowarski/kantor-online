package com.example.kantoronline.mapper;

import com.example.kantoronline.dtos.AddAccountDto;
import com.example.kantoronline.entities.Account;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AccountMapper {

    public static Account map(AddAccountDto addAccountDto) {
        return Account.builder()
                .email(addAccountDto.getEmail())
                .password(addAccountDto.getPassword())
                .build();
    }
}
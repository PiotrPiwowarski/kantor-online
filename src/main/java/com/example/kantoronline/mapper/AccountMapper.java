package com.example.kantoronline.mapper;

import com.example.kantoronline.dtos.AccountDto;
import com.example.kantoronline.dtos.AddAccountDto;
import com.example.kantoronline.entities.Account;
import com.example.kantoronline.enums.AccountRole;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AccountMapper {

    public static Account map(AddAccountDto addAccountDto, PasswordEncoder passwordEncoder) {
        return Account.builder()
                .firstName(addAccountDto.getFirstName())
                .lastName(addAccountDto.getLastName())
                .email(addAccountDto.getEmail())
                .password(passwordEncoder.encode(addAccountDto.getPassword()))
                .accountRole(AccountRole.USER)
                .build();
    }

    public static AccountDto map(Account account) {
        return AccountDto.builder()
                .id(account.getId())
                .firstName(account.getFirstName())
                .lastName(account.getLastName())
                .email(account.getEmail())
                .accountRole(account.getAccountRole())
                .build();
    }
}

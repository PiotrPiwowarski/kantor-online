package com.example.kantoronline.services.account;

import com.example.kantoronline.dtos.AccountDto;
import com.example.kantoronline.dtos.AddAccountDto;
import com.example.kantoronline.dtos.AuthenticationDto;
import com.example.kantoronline.dtos.LoginDto;
import com.example.kantoronline.entities.Account;
import org.springframework.stereotype.Service;

@Service
public interface AccountService {

    long addAccount(AddAccountDto addAccountDto);
    void deleteAccount(long id);
    AccountDto getAccountDto(long id);
    Account getAccount(long id);
    AuthenticationDto login(LoginDto loginDto);
    void logout();
}

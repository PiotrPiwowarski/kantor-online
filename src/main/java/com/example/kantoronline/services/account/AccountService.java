package com.example.kantoronline.services.account;

import com.example.kantoronline.dtos.AddAccountDto;
import org.springframework.stereotype.Service;

@Service
public interface AccountService {

    long addAccount(AddAccountDto addAccountDto);
    void deleteAccount(long id);
}

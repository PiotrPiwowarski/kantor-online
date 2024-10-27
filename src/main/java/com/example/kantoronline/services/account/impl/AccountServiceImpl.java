package com.example.kantoronline.services.account.impl;

import com.example.kantoronline.dtos.AccountDto;
import com.example.kantoronline.dtos.AddAccountDto;
import com.example.kantoronline.dtos.AuthenticationDto;
import com.example.kantoronline.dtos.LoginDto;
import com.example.kantoronline.entities.Account;
import com.example.kantoronline.exceptions.AccountWithSuchEmailAlreadyExistsException;
import com.example.kantoronline.exceptions.NoAccountWithSuchIdException;
import com.example.kantoronline.mapper.AccountMapper;
import com.example.kantoronline.repositories.AccountRepository;
import com.example.kantoronline.services.account.AccountService;
import com.example.kantoronline.services.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public long addAccount(AddAccountDto addAccountDto) {
        Optional<Account> optionalAccount = accountRepository.findByEmail(addAccountDto.getEmail());
        if(optionalAccount.isPresent()) {
            throw new AccountWithSuchEmailAlreadyExistsException();
        }
        Account account = AccountMapper.map(addAccountDto,passwordEncoder);
        Account savedAccount = accountRepository.save(account);
        return savedAccount.getId();
    }

    @Override
    public void deleteAccount(long id) {
        accountRepository.deleteById(id);
    }

    @Override
    public AccountDto getAccountDto(long id) {
        Account account = accountRepository.findById(id).orElseThrow(NoAccountWithSuchIdException::new);
        return AccountMapper.map(account);
    }

    @Override
    public Account getAccount(long accountId) {
        Optional<Account> optionalAccount = accountRepository.findById(accountId);
        if(optionalAccount.isEmpty()) {
            throw new NoAccountWithSuchIdException();
        }
        return optionalAccount.get();
    }

    @Override
    public AuthenticationDto login(LoginDto loginDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getEmail(),
                        loginDto.getPassword()
                )
        );
        Account account = accountRepository
                .findByEmail(loginDto.getEmail())
                .orElseThrow(NoAccountWithSuchIdException::new);
        String jwtToken = jwtService.generateToken(account);
        return AuthenticationDto.builder()
                .token(jwtToken)
                .build();
    }

    @Override
    public void logout() {

    }
}

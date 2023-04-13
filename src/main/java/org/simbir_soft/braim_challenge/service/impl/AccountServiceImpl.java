package org.simbir_soft.braim_challenge.service.impl;

import lombok.RequiredArgsConstructor;
import org.simbir_soft.braim_challenge.aspect.annotation.CheckAuth;
import org.simbir_soft.braim_challenge.aspect.annotation.ExistingId;
import org.simbir_soft.braim_challenge.domain.Account;
import org.simbir_soft.braim_challenge.domain.dto.Dto;
import org.simbir_soft.braim_challenge.exception.DataConflictException;
import org.simbir_soft.braim_challenge.exception.DataInvalidException;
import org.simbir_soft.braim_challenge.exception.DataMissingException;
import org.simbir_soft.braim_challenge.repository.AccountRepository;
import org.simbir_soft.braim_challenge.service.AccountService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService, UserDetailsService {

    private final AccountRepository repository;
    private final PasswordEncoder encoder;

    private void checkEmail(String email) {
        if (repository.existsByEmail(email)) {
            throw new DataConflictException();
        }
    }

    private void checkEmailById(Long id, String email) {
        Optional<Account> optionalAccount = repository.findByEmail(email);
        if (optionalAccount.isEmpty()) {
            return;
        }
        Account account = optionalAccount.get();
        if (!account.getId().equals(id)) {
            throw new DataConflictException();
        }
    }

    @Override
    public Account save(Dto<Account> dto) {
        Account dtoAccount = dto.fromDto();
        dtoAccount.setPassword(encoder.encode(dtoAccount.getPassword()));
        checkEmail(dtoAccount.getEmail());

        return repository.save(dtoAccount);
    }


    @Override
    @CheckAuth
    @ExistingId(validator = AccountService.class)
    public Account update(Long id, Dto<Account> dto) {
        Account dtoAccount = dto.fromDto();
        checkEmailById(id, dtoAccount.getEmail());
        dtoAccount.setId(id);
        dtoAccount.setPassword(encoder.encode(dtoAccount.getPassword()));
        return repository.save(dtoAccount);
    }


    @Override
    @CheckAuth
    @ExistingId(validator = AccountService.class)
    public void delete(Long id) {
        try {
            repository.deleteById(id);
        } catch (RuntimeException e) {
            throw new DataInvalidException();
        }
    }

    @Override
    @CheckAuth
    public Optional<Account> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Optional<Account> findByIdInternal(Long id) {
        return repository.findById(id);
    }

    @Override
    public Optional<Account> findByEmail(String email) {
        return repository.findByEmail(email);
    }

    @Override
    public Iterable<Account> findAllById(Iterable<Long> ids) {
        Iterable<Account> accounts = repository.findAllById(ids);

        if (accounts.spliterator().getExactSizeIfKnown() != accounts.spliterator().getExactSizeIfKnown()) {
            throw new DataMissingException();
        }
        return accounts;
    }

    @Override
    public Iterable<Account> findAll() {
        return repository.findAll();
    }

    @Override
    public boolean existsById(Long id) {
        return repository.existsById(id);
    }


    @Override
    public Account register(Account account) {
        account.setPassword(encoder.encode(account.getPassword()));
        return repository.save(account);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Account account = repository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email));
        return User.builder()
                .username(account.getEmail())
                .password(account.getPassword())
                .roles(account.getRole().name())
                .build();
    }
}

package org.simbir_soft.braim_challenge.service.impl;

import lombok.RequiredArgsConstructor;
import org.simbir_soft.braim_challenge.domain.Account;
import org.simbir_soft.braim_challenge.domain.dto.Dto;
import org.simbir_soft.braim_challenge.exception.NonUniqueEmailException;
import org.simbir_soft.braim_challenge.repository.AccountRepository;
import org.simbir_soft.braim_challenge.service.AccountService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository repository;

    @Override
    public Account save(Dto<Account> dto) {
        Account dtoAccount = dto.fromDto();
        if (repository.existsByEmail(dtoAccount.getEmail())) {
            throw new NonUniqueEmailException();
        }

        return repository.save(dtoAccount);
    }

    @Override
    public Account update(Long aLong, Dto<Account> dto) {
        return null;
    }

    @Override
    public void delete(Long aLong) {

    }

    @Override
    public Optional<Account> find(Long aLong) {
        return Optional.empty();
    }

    @Override
    public Collection<Account> findAll() {
        return null;
    }
}

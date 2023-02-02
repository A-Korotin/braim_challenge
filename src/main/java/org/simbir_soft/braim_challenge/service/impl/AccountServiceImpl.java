package org.simbir_soft.braim_challenge.service.impl;

import lombok.RequiredArgsConstructor;
import org.simbir_soft.braim_challenge.domain.Account;
import org.simbir_soft.braim_challenge.domain.dto.Dto;
import org.simbir_soft.braim_challenge.exception.InvalidAccountIdException;
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

    private void checkEmail(String email) {
        if (repository.existsByEmail(email)) {
            throw new NonUniqueEmailException();
        }
    }

    private void checkId(Long id) {
        if (!repository.existsById(id)) {
            throw new InvalidAccountIdException();
        }
    }

    @Override
    public Account save(Dto<Account> dto) {
        Account dtoAccount = dto.fromDto();

        checkEmail(dtoAccount.getEmail());

        return repository.save(dtoAccount);
    }

    private void checkEmailById(Long id, String email) {
        Optional<Account> optionalAccount = repository.findByEmail(email);
        if (optionalAccount.isEmpty()) {
            return;
        }
        Account account = optionalAccount.get();
        if (!account.getId().equals(id)) {
            throw new NonUniqueEmailException();
        }
    }

    @Override
    public Account update(Long id, Dto<Account> dto) {
        checkId(id);
        Account dtoAccount = dto.fromDto();
        checkEmailById(id, dtoAccount.getEmail());
        dtoAccount.setId(id);
        return repository.save(dtoAccount);

    }

    @Override
    public void delete(Long aLong) {

    }

    @Override
    public Optional<Account> find(Long id) {
        return repository.findById(id);
    }

    @Override
    public Collection<Account> findAll() {
        return null;
    }

    @Override
    public boolean existsById(Long id) {
        return repository.existsById(id);
    }
}

package org.simbir_soft.braim_challenge.service.impl;

import lombok.RequiredArgsConstructor;
import org.simbir_soft.braim_challenge.domain.Account;
import org.simbir_soft.braim_challenge.domain.dto.Dto;
import org.simbir_soft.braim_challenge.exception.AccessForbiddenException;
import org.simbir_soft.braim_challenge.exception.InvalidAccountIdException;
import org.simbir_soft.braim_challenge.exception.DataConflictException;
import org.simbir_soft.braim_challenge.repository.AccountRepository;
import org.simbir_soft.braim_challenge.service.AccountService;
import org.springframework.security.core.context.SecurityContextHolder;
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

    private void checkId(Long id) {
        if (!repository.existsById(id)) {
            throw new InvalidAccountIdException();
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

    private void checkIfCurrentUserIsAllowedToModify(Long id) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<Account> accountOptional = repository.findByEmail(name);
        if (accountOptional.isEmpty()) {
            throw new AccessForbiddenException();
        }

        if (!accountOptional.get().getId().equals(id)) {
            throw new AccessForbiddenException();
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
    public Account update(Long id, Dto<Account> dto) {
        checkId(id);
        checkIfCurrentUserIsAllowedToModify(id);

        Account dtoAccount = dto.fromDto();
        checkEmailById(id, dtoAccount.getEmail());
        dtoAccount.setId(id);
        dtoAccount.setPassword(encoder.encode(dtoAccount.getPassword()));
        return repository.save(dtoAccount);
    }


    @Override
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new AccessForbiddenException();
        }
        checkIfCurrentUserIsAllowedToModify(id);

        repository.deleteById(id);
    }

    @Override
    public Optional<Account> find(Long id) {
        return repository.findById(id);
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
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Account account = repository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email));
        return User.builder()
                .username(account.getEmail())
                .password(account.getPassword())
                .roles("User")
                .build();
    }
}

package org.simbir_soft.braim_challenge.service;

import org.simbir_soft.braim_challenge.domain.Account;

import java.util.Optional;

public interface AccountService extends CrudService<Account, Long> {
    Optional<Account> findByEmail(String email);

    Account register(Account account);

    Optional<Account> findByIdInternal(Long id);
}

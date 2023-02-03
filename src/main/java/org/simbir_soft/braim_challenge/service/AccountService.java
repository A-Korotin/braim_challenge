package org.simbir_soft.braim_challenge.service;

import org.simbir_soft.braim_challenge.domain.Account;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface AccountService extends CrudService<Account, Long> {
    Optional<Account> findByEmail(String email);
}

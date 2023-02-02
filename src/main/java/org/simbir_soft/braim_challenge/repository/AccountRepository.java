package org.simbir_soft.braim_challenge.repository;

import org.simbir_soft.braim_challenge.domain.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends CrudRepository<Account, Long> {
    boolean existsByEmail(String email);
    Optional<Account> findByEmail(String email);
}

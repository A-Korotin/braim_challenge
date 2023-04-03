package org.simbir_soft.braim_challenge.config;

import lombok.RequiredArgsConstructor;
import org.simbir_soft.braim_challenge.domain.Account;
import org.simbir_soft.braim_challenge.domain.UserRole;
import org.simbir_soft.braim_challenge.domain.dto.AccountDto;
import org.simbir_soft.braim_challenge.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DatabaseSetup implements CommandLineRunner {
    private final AccountService service;

    private final List<Account> initialAccounts = new ArrayList<>();

    @Autowired
    public DatabaseSetup(AccountService service) {
        this.service = service;
        initialAccounts.add(new Account("adminFirstName", "adminLastName", "admin@simbirsoft.com", UserRole.ADMIN, "qwerty123"));
        initialAccounts.add(new Account("chipperFirstName", "chipperLastName", "chipper@simbirsoft.com", UserRole.CHIPPER, "qwerty123"));
        initialAccounts.add(new Account("userFirstName", "userLastName", "user@simbirsoft.com", UserRole.USER, "qwerty123"));
    }

    @Override
    public void run(String... args) throws Exception {
        for (var account: initialAccounts) {
            if (service.findByEmail(account.getEmail()).isEmpty()) {
                service.register(account);
            }
        }
    }
}

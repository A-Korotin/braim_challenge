package org.simbir_soft.braim_challenge.config;

import lombok.RequiredArgsConstructor;
import org.simbir_soft.braim_challenge.domain.dto.AccountDto;
import org.simbir_soft.braim_challenge.service.AccountService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DatabaseSetup implements CommandLineRunner {
    private final AccountService service;

    @Override
    public void run(String... args) throws Exception {
        if (service.findByEmail("sx12fx@gmail.com").isEmpty()) {
            AccountDto accountDto = new AccountDto();
            accountDto.setFirstName("Alexey");
            accountDto.setLastName("Korotin");
            accountDto.setEmail("sx12fx@gmail.com");
            accountDto.setPassword("123456");
            service.save(accountDto);
        }
    }
}

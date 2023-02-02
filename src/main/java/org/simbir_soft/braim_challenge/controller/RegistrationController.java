package org.simbir_soft.braim_challenge.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.simbir_soft.braim_challenge.domain.Account;
import org.simbir_soft.braim_challenge.domain.dto.AccountDto;
import org.simbir_soft.braim_challenge.exception.NonUniqueEmailException;
import org.simbir_soft.braim_challenge.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RegistrationController {

    private final AccountService accountService;

    @PostMapping("/registration")
    public ResponseEntity<?> registerAccount(@Valid @RequestBody AccountDto accountDto) {
        Account account = accountService.save(accountDto);

        return ResponseEntity.ok(account);
    }
}

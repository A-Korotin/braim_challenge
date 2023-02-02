package org.simbir_soft.braim_challenge.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.simbir_soft.braim_challenge.domain.Account;
import org.simbir_soft.braim_challenge.domain.dto.AccountDto;
import org.simbir_soft.braim_challenge.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/registration")
    public ResponseEntity<?> registerAccount(@Valid @RequestBody AccountDto accountDto) {
        Account account = accountService.save(accountDto);

        return ResponseEntity.ok(account);
    }

    @PutMapping("/accounts/{accountId}")
    public ResponseEntity<?> updateAccount(@Valid @RequestBody AccountDto accountDto,
                                           @PathVariable Long accountId) {

        return ResponseEntity.ok(accountService.update(accountId, accountDto));

    }
}

package org.simbir_soft.braim_challenge.controller;

import jakarta.validation.Valid;
import org.simbir_soft.braim_challenge.domain.Account;
import org.simbir_soft.braim_challenge.domain.dto.AccountDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegistrationController {

    @PostMapping("/registration")
    public ResponseEntity<?> registerAccount(@Valid @RequestBody AccountDto accountDto) {
        System.out.println(accountDto);

        return ResponseEntity.ok(new Account(accountDto));
    }
}

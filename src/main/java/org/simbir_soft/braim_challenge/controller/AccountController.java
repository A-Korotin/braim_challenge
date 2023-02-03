package org.simbir_soft.braim_challenge.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.simbir_soft.braim_challenge.domain.Account;
import org.simbir_soft.braim_challenge.domain.BaseEntity;
import org.simbir_soft.braim_challenge.domain.dto.AccountDto;
import org.simbir_soft.braim_challenge.exception.InvalidAccountIdException;
import org.simbir_soft.braim_challenge.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequiredArgsConstructor
@Validated
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/registration")
    public ResponseEntity<?> registerAccount(@Valid @RequestBody AccountDto accountDto) {
        Account account = accountService.save(accountDto);

        return ResponseEntity.ok(account);
    }

    @PutMapping("/accounts/{accountId}")
    public ResponseEntity<?> updateAccount(@Valid @RequestBody AccountDto accountDto,
                                           @Min(value = 1) @PathVariable Long accountId) {

        return ResponseEntity.ok(accountService.update(accountId, accountDto));

    }

    @GetMapping("/accounts/{accountId}")
    public ResponseEntity<?> getAccount(@PathVariable @NotNull @Min(value = 1) Long accountId) {

        return ResponseEntity.ok(accountService.findById(accountId).orElseThrow(InvalidAccountIdException::new));
    }

    @GetMapping("/accounts/search")
    public ResponseEntity<?> searchAccounts(@RequestParam String firstName,
                                            @RequestParam String lastName,
                                            @RequestParam String email,
                                            @RequestParam(defaultValue = "0")  @NotNull @Min(value = 0)  Long from,
                                            @RequestParam(defaultValue = "10") @NotNull @Min(value = 1) Long size) {

        List<Account> filtered = StreamSupport.stream(accountService.findAll().spliterator(), false)
                .filter(account -> account.getFirstName().toLowerCase().contains(firstName.toLowerCase()))
                .filter(account -> account.getLastName().toLowerCase().contains(lastName.toLowerCase()))
                .filter(account -> account.getEmail().toLowerCase().contains(email.toLowerCase()))
                .sorted(Comparator.comparingLong(BaseEntity::getId))
                .skip(from)
                .limit(size)
                .collect(Collectors.toList());

        return ResponseEntity.ok(filtered);
    }

    @DeleteMapping("/accounts/{accountId}")
    public ResponseEntity<?> deleteAccount(@NotNull @Min(value = 1) @PathVariable Long accountId) {
        accountService.delete(accountId);
        return ResponseEntity.ok().build();
    }
}

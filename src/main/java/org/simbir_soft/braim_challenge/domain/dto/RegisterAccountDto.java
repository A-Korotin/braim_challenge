package org.simbir_soft.braim_challenge.domain.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.simbir_soft.braim_challenge.domain.Account;
import org.simbir_soft.braim_challenge.domain.UserRole;

@Data
public class RegisterAccountDto implements Dto<Account> {
    @NotNull
    @NotBlank
    private String firstName;

    @NotNull
    @NotBlank
    private String lastName;

    @NotNull
    @NotBlank
    @Email
    private String email;

    @NotNull
    @NotBlank
    private String password;

    @Override
    public Account fromDto() {
        return Account.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .role(UserRole.USER)
                .password(password).build();
    }
}

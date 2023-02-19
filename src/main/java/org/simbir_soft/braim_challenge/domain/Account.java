package org.simbir_soft.braim_challenge.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.simbir_soft.braim_challenge.domain.dto.AccountDto;

@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_account")
@Inheritance(strategy = InheritanceType.JOINED)
public class Account extends BaseEntity {

    private String firstName;
    private String lastName;
    private String email;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    private String password;
}

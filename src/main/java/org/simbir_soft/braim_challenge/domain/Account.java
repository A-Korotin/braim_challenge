package org.simbir_soft.braim_challenge.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

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

    @Enumerated(value = EnumType.STRING)
    private UserRole role;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    private String password;
}

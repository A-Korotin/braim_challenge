package org.simbir_soft.braim_challenge.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "animal_type")
@Inheritance(strategy = InheritanceType.JOINED)
public class AnimalType extends BaseEntity {
    private String name;
}

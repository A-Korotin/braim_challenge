package org.simbir_soft.braim_challenge.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import lombok.*;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "location")
@Inheritance(strategy = InheritanceType.JOINED)
public class Location extends BaseEntity {
    private BigDecimal latitude;
    private BigDecimal longitude;
}

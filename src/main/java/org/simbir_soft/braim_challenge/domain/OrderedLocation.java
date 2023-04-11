package org.simbir_soft.braim_challenge.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "area_location_relation")
@Inheritance(strategy = InheritanceType.JOINED)
public class OrderedLocation extends BaseEntity {
    @JsonIgnore
    @Column(name = "location_order")
    private long order;

    private BigDecimal longitude;

    private BigDecimal latitude;

    @JsonIgnore
    @ManyToOne
    @ToString.Exclude
    @JoinColumn(name = "area_id")
    private Area area;
}

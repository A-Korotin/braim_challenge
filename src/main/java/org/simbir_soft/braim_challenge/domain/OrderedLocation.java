package org.simbir_soft.braim_challenge.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

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

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;
}

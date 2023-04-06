package org.simbir_soft.braim_challenge.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "area") // todo table name
@Inheritance(strategy = InheritanceType.JOINED)
public class Area extends BaseEntity {
    private String name;

    @ManyToMany
    @JoinTable(
            name = "area_location_relation",
            joinColumns = @JoinColumn(name = "area_id"),
            inverseJoinColumns = @JoinColumn(name = "location_id")
    )
    private List<Location> areaPoints;
}

package org.simbir_soft.braim_challenge.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.simbir_soft.braim_challenge.jsonSerializer.CustomEntitySerializer;
import org.simbir_soft.braim_challenge.jsonSerializer.CustomEntityListSerializer;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "animal")
@Inheritance(strategy = InheritanceType.JOINED)
public class Animal extends BaseEntity {
    public enum Gender {
        MALE,
        FEMALE,
        OTHER
    }

    public enum LifeStatus {
        ALIVE,
        DEAD
    }

    @JsonSerialize(using = CustomEntityListSerializer.class)
    @ManyToMany
    @JoinTable(
            name = "animal_type_relation",
            joinColumns = @JoinColumn(name = "animal_id"),
            inverseJoinColumns = @JoinColumn(name = "animal_type_id")
    )
    private List<AnimalType> animalTypes = new ArrayList<>();

    private BigDecimal weight;
    private BigDecimal length;
    private BigDecimal height;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    private LifeStatus lifeStatus = LifeStatus.ALIVE;

    private LocalDateTime chippingDateTime;


    @JsonProperty(value = "chipperId")
    @JsonSerialize(using = CustomEntitySerializer.class)
    @ManyToOne
    @JoinColumn(name = "chipper_id")
    private Account chipper;


    @JsonProperty(value = "chippingLocationId")
    @JsonSerialize(using = CustomEntitySerializer.class)
    @ManyToOne
    @JoinColumn(name = "chipping_location_id")
    private Location chippingLocation;

    @JsonSerialize(using = CustomEntityListSerializer.class)
    @ManyToMany
    @JoinTable(
            name = "animal_location_relation",
            joinColumns = @JoinColumn(name = "animal_id"),
            inverseJoinColumns = @JoinColumn(name = "location_id")
    )
    private List<Location> visitedLocations;

    private LocalDateTime deathDateTime = null;
}

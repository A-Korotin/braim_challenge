package org.simbir_soft.braim_challenge.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.*;
import org.simbir_soft.braim_challenge.json.serializer.CustomEntityListSerializer;
import org.simbir_soft.braim_challenge.json.serializer.CustomEntitySerializer;
import org.simbir_soft.braim_challenge.json.serializer.TimedLocationListSerializer;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
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
    @Builder.Default
    private List<AnimalType> animalTypes = new ArrayList<>();

    private BigDecimal weight;
    private BigDecimal length;
    private BigDecimal height;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    private LifeStatus lifeStatus = LifeStatus.ALIVE;

    private ZonedDateTime chippingDateTime;


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

    @JsonSerialize(using = TimedLocationListSerializer.class)
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.REFRESH})
    @JoinColumn(name = "animal_id")
    @Builder.Default
    private List<TimedLocation> visitedLocations = new ArrayList<>();

    private ZonedDateTime deathDateTime = null;

    public boolean leftChippingLocation() {
        int size = visitedLocations.size();
        return size > 0 &&
                !visitedLocations.get(size - 1).getLocation()
                        .equals(chippingLocation); // last location is not a chipping location

    }

    public boolean hasType(Long typeId) {
        return animalTypes.stream().anyMatch(t -> t.getId().equals(typeId));
    }

    public boolean removeType(Long typeId) {
        if (animalTypes.size() == 1 && animalTypes.get(0).getId().equals(typeId)) {
            return false;
        }

        return animalTypes.removeIf(t -> t.getId().equals(typeId));
    }

    public boolean isDead() {
        return lifeStatus.equals(LifeStatus.DEAD);
    }
    public boolean isAlive() {
        return lifeStatus.equals(LifeStatus.ALIVE);
    }
}

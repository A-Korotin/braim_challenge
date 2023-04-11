package org.simbir_soft.braim_challenge.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.*;
import org.simbir_soft.braim_challenge.json.serializer.CustomAreaPointsSerializer;

import java.util.List;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "area")
@Inheritance(strategy = InheritanceType.JOINED)
public class Area extends BaseEntity {
    private String name;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "area_id")
    @JsonSerialize(using = CustomAreaPointsSerializer.class)
    private List<OrderedLocation> areaPoints;
}

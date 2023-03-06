package org.simbir_soft.braim_challenge.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.simbir_soft.braim_challenge.json.serializer.CustomEntitySerializer;

import java.time.ZonedDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "animal_location_relation")
public class TimedLocation extends BaseEntity {

    @JsonProperty(value = "locationPointId")
    @JsonSerialize(using = CustomEntitySerializer.class)
    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "animal_id")
    private Animal animal;

    @JsonProperty(value = "dateTimeOfVisitLocationPoint")
    @Column(name = "visit_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
    private ZonedDateTime visitTime;
}

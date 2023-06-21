package org.simbir_soft.braim_challenge.domain.analytics;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class AnimalTypeAnalytic implements AnalyticIncrementable{
    private final String animalType;
    private final Long animalTypeId;

    @JsonProperty(value = "quantityAnimals")
    private Long total = 0L;

    @JsonProperty(value = "animalsArrived")
    private Long arrived = 0L;

    @JsonProperty(value = "animalsGone")
    private Long gone = 0L;


    @Override
    public void addTotal() {
        total++;
    }
    @Override
    public void addArrived() {
        arrived++;
    }

    @Override
    public void addGone() {
        gone++;
    }
}

package org.simbir_soft.braim_challenge.domain.analytics;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Analytics implements AnalyticIncrementable {
    @JsonProperty(value = "totalQuantityAnimals")
    private Long total = 0L;

    @JsonProperty(value = "totalAnimalsArrived")
    private Long arrived = 0L;

    @JsonProperty(value = "totalAnimalsGone")
    private Long gone = 0L;

    private List<AnimalTypeAnalytic> animalsAnalytics = new ArrayList<>();


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

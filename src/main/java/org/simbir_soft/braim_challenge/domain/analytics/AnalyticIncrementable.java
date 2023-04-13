package org.simbir_soft.braim_challenge.domain.analytics;

public interface AnalyticIncrementable {
    void addTotal();
    void addGone();
    void addArrived();
}

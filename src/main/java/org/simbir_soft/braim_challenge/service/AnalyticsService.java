package org.simbir_soft.braim_challenge.service;

import org.simbir_soft.braim_challenge.domain.analytics.Analytics;

import java.time.LocalDate;

public interface AnalyticsService {
    Analytics getAnalytics(Long areaId, LocalDate start, LocalDate end);
}

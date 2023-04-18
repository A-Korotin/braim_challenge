package org.simbir_soft.braim_challenge.service.impl;

import lombok.RequiredArgsConstructor;
import org.simbir_soft.braim_challenge.aspect.annotation.ExistingId;
import org.simbir_soft.braim_challenge.domain.*;
import org.simbir_soft.braim_challenge.domain.analytics.AnalyticIncrementable;
import org.simbir_soft.braim_challenge.domain.analytics.Analytics;
import org.simbir_soft.braim_challenge.domain.analytics.AnimalTypeAnalytic;
import org.simbir_soft.braim_challenge.service.AnalyticsService;
import org.simbir_soft.braim_challenge.service.AnimalService;
import org.simbir_soft.braim_challenge.service.AreaService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.chrono.ChronoZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class AnalyticsServiceImpl implements AnalyticsService {

    private final AnimalService animalService;
    private final AreaService areaService;

    public boolean isExit(Area area, Location previous, Location current) {
        return area.locationInside(previous) && !area.locationInside(current);
    }
    public boolean isEnter(Area area, Location previous, Location current) {
        return !area.locationInside(previous) && area.locationInside(current);
    }

    private void performCheck(AnalyticIncrementable analytic, Area area, Animal animal, LocalDate start, LocalDate end) {
        if (animal.getLastDateTime().isAfter(start.atStartOfDay(ZoneId.systemDefault())) &&
                animal.getLastDateTime().isBefore(end.atStartOfDay(ZoneId.systemDefault())) &&
                area.locationInside(animal.getLastLocation())) {
            analytic.addTotal();
        }

        boolean countExit = false,
                countEnter = false;
        Location previous = animal.getChippingLocation();
        for (TimedLocation location : animal.getVisitedLocations()) {
            if (!location.getVisitTime().isAfter(start.atStartOfDay(ZoneId.systemDefault())) ||
                    !location.getVisitTime().isBefore(end.atStartOfDay(ZoneId.systemDefault()))) {
                previous = location.getLocation();
                continue;
            }

            if (isEnter(area, previous, location.getLocation()) && !countEnter) {
                analytic.addArrived();
                countEnter = true;
            }

            if (isExit(area, previous, location.getLocation()) && !countExit) {
                analytic.addGone();
                countExit = true;
            }

            previous = location.getLocation();
        }
    }

    private List<AnimalTypeAnalytic> countByType(Area area, Iterable<Animal> animals, LocalDate start, LocalDate end) {
        Map<AnimalType, AnimalTypeAnalytic> analyticMap = new HashMap<>();
        for (Animal animal: animals) {
            for (AnimalType animalType: animal.getAnimalTypes()) {
                AnimalTypeAnalytic analytic = analyticMap.get(animalType);
                analytic = analytic == null ? new AnimalTypeAnalytic(animalType.getType(), animalType.getId()):analytic;
                performCheck(analytic, area, animal, start, end);

                analyticMap.put(animalType, analytic);
            }
        }

        return analyticMap.values().stream()
                .filter(a -> a.getGone() != 0 || a.getArrived() != 0 || a.getTotal() != 0)
                .toList();
    }

    private Analytics countTotal(Area area, Iterable<Animal> animals, LocalDate start, LocalDate end) {
        Analytics analytics = new Analytics();

        for (Animal animal: animals) {
            performCheck(analytics, area, animal, start, end);
        }

        return analytics;
    }

    @Override
    @ExistingId(validator = AreaService.class)
    public Analytics getAnalytics(Long areaId, LocalDate start, LocalDate end) {
        Iterable<Animal> animals = animalService.findAll();
        Area area = areaService.findById(areaId).get();
        Analytics analytics = countTotal(area, animals, start, end);
        analytics.setAnimalsAnalytics(countByType(area, animals, start, end));
        return analytics;
    }
}

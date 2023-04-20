package org.simbir_soft.braim_challenge.controller;

import ch.hsr.geohash.GeoHash;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.simbir_soft.braim_challenge.domain.dto.LocationDto;
import org.simbir_soft.braim_challenge.exception.DataMissingException;
import org.simbir_soft.braim_challenge.service.GeoHashService;
import org.simbir_soft.braim_challenge.service.LocationService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/locations")
public class LocationController {
    private final LocationService locationService;
    private final GeoHashService geoHashService;

    @PostMapping
    public ResponseEntity<?> createLocation(@Valid @RequestBody LocationDto locationDto) {
        return ResponseEntity.status(201).body(locationService.save(locationDto));
    }

    @GetMapping("/{locationId}")
    public ResponseEntity<?> getLocation(@PathVariable @Min(value = 1) Long locationId) {
        return ResponseEntity.ok(locationService.findById(locationId).orElseThrow(DataMissingException::new));
    }

    @PutMapping("/{locationId}")
    public ResponseEntity<?> updateLocation(@PathVariable @Min(value = 1) Long locationId,
                                            @Valid @RequestBody LocationDto locationDto) {
        return ResponseEntity.ok(locationService.update(locationId, locationDto));
    }

    @DeleteMapping("/{locationId}")
    public ResponseEntity<?> deleteLocation(@PathVariable @Min(value = 1) Long locationId) {
        locationService.delete(locationId);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<?> findLocation(@RequestParam
                                              @DecimalMin("-90")
                                              @DecimalMax("90")
                                              BigDecimal latitude,
                                          @RequestParam
                                              @DecimalMin("-180")
                                              @DecimalMax("180")
                                              BigDecimal longitude) {
        return ResponseEntity.ok(locationService.findByLatitudeAndLongitude(latitude, longitude)
                .orElseThrow(DataMissingException::new).getId());
    }

    @GetMapping("/geohash")
    public ResponseEntity<?> getHash(@RequestParam
                                         @DecimalMin("-90")
                                         @DecimalMax("90")
                                         BigDecimal latitude,
                                     @RequestParam
                                         @DecimalMin("-180")
                                         @DecimalMax("180")
                                         BigDecimal longitude) {
        return ResponseEntity.ok(geoHashService.getHashV1(longitude, latitude));
    }
    @GetMapping("/geohashv2")
    public ResponseEntity<?> getHashv2(@RequestParam
                                           @DecimalMin("-90")
                                           @DecimalMax("90")
                                           BigDecimal latitude,
                                     @RequestParam
                                         @DecimalMin("-180")
                                         @DecimalMax("180")
                                         BigDecimal longitude) {
        return ResponseEntity.ok(geoHashService.getHashV2(longitude, latitude));
    }
    @GetMapping("/geohashv3")
    public ResponseEntity<?> getHashv3(@RequestParam
                                           @DecimalMin("-90")
                                           @DecimalMax("90")
                                           BigDecimal latitude,
                                     @RequestParam
                                          @DecimalMin("-180")
                                          @DecimalMax("180")
                                          BigDecimal longitude) throws Throwable {
        return ResponseEntity.ok(geoHashService.getHashV3(longitude, latitude));
    }
}

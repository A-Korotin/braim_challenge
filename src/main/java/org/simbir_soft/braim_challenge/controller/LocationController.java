package org.simbir_soft.braim_challenge.controller;

import ch.hsr.geohash.GeoHash;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.simbir_soft.braim_challenge.domain.dto.LocationDto;
import org.simbir_soft.braim_challenge.exception.DataMissingException;
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
        return ResponseEntity.ok(GeoHash.geoHashStringWithCharacterPrecision(latitude.doubleValue(), longitude.doubleValue(), 12));
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
        String hash = GeoHash.withCharacterPrecision(latitude.doubleValue(), longitude.doubleValue(), 12).toBase32();
        byte[] bytes = hash.getBytes(StandardCharsets.UTF_8);
        return ResponseEntity.ok(new String(Base64.getEncoder().encode(bytes)));
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
        String hash = GeoHash.withCharacterPrecision(latitude.doubleValue(), longitude.doubleValue(), 12).toBase32();
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        byte[] encoded = md5.digest(hash.getBytes(StandardCharsets.UTF_8));
        int i = encoded.length - 1, j = 0;
        while (i > j) {
            byte tmp = encoded[i];
            encoded[i] = encoded[j];
            encoded[j] = tmp;
            i--;
            j++;
        }

        return ResponseEntity.ok(Base64.getEncoder().encode(encoded));
    }
}

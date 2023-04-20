package org.simbir_soft.braim_challenge.service;

import java.math.BigDecimal;

public interface GeoHashService {
    String getHashV1(BigDecimal longitude, BigDecimal latitude);
    String getHashV2(BigDecimal longitude, BigDecimal latitude);
    String getHashV3(BigDecimal longitude, BigDecimal latitude) throws Throwable;
}

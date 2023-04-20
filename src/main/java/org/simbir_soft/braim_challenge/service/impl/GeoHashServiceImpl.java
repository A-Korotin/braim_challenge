package org.simbir_soft.braim_challenge.service.impl;

import ch.hsr.geohash.GeoHash;
import org.simbir_soft.braim_challenge.service.GeoHashService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;

@Service
public class GeoHashServiceImpl implements GeoHashService {
    @Override
    public String getHashV1(BigDecimal longitude, BigDecimal latitude) {
        return GeoHash.geoHashStringWithCharacterPrecision(latitude.doubleValue(),
                longitude.doubleValue(), 12);
    }

    @Override
    public String getHashV2(BigDecimal longitude, BigDecimal latitude) {
        String hash = getHashV1(longitude, latitude);

        byte[] bytes = hash.getBytes(StandardCharsets.UTF_8);
        return new String(Base64.getEncoder().encode(bytes));
    }

    @Override
    public String getHashV3(BigDecimal longitude, BigDecimal latitude) throws Throwable {
        String hash = getHashV1(longitude, latitude);

        MessageDigest md5 = MessageDigest.getInstance("MD5");

        byte[] encoded = md5.digest(hash.getBytes(StandardCharsets.UTF_8));
        // разворот массива
        int i = encoded.length - 1, j = 0;
        while (i > j) {
            byte tmp = encoded[i];
            encoded[i] = encoded[j];
            encoded[j] = tmp;
            i--;
            j++;
        }

        return new String(Base64.getEncoder().encode(encoded));
    }
}

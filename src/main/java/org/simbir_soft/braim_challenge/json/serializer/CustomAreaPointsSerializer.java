package org.simbir_soft.braim_challenge.json.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.simbir_soft.braim_challenge.domain.OrderedLocation;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public class CustomAreaPointsSerializer extends StdSerializer<List<OrderedLocation>> {

    public CustomAreaPointsSerializer() {
        this(null);
    }

    public CustomAreaPointsSerializer(Class<List<OrderedLocation>> clazz) {
        super(clazz);
    }


    @Data
    @AllArgsConstructor
    private static class Location {
        private BigDecimal longitude, latitude;
    }

    @Override
    public void serialize(List<OrderedLocation> value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        List<Location> out = value.stream().map(v ->
                new Location(v.getLocation().getLongitude(),
                             v.getLocation().getLatitude()))
                .toList();
        gen.writePOJO(out);
    }
}

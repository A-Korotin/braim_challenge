package org.simbir_soft.braim_challenge.json.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.simbir_soft.braim_challenge.domain.BaseEntity;
import org.simbir_soft.braim_challenge.domain.TimedLocation;

import java.io.IOException;
import java.util.List;

public class TimedLocationListSerializer extends StdSerializer<List<TimedLocation>> {

    public TimedLocationListSerializer() {
        this(null);
    }

    public TimedLocationListSerializer(Class<List<TimedLocation>> clazz) {
        super(clazz);
    }

    @Override
    public void serialize(List<TimedLocation> value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        long[] ids = value.stream().mapToLong(tl -> tl.getLocation().getId()).toArray();
        gen.writeArray(ids, 0, ids.length);
    }
}

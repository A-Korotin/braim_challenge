package org.simbir_soft.braim_challenge.jsonSerializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.simbir_soft.braim_challenge.domain.BaseEntity;

import java.io.IOException;
import java.util.List;

public class CustomEntityListSerializer extends StdSerializer<List<? extends BaseEntity>> {

    public CustomEntityListSerializer() {
        this(null);
    }

    public CustomEntityListSerializer(Class<List<? extends BaseEntity>> clazz) {
        super(clazz);
    }

    @Override
    public void serialize(List<? extends BaseEntity> value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        long[] ids = value.stream().mapToLong(BaseEntity::getId).toArray();
        gen.writeArray(ids, 0, ids.length);
    }
}

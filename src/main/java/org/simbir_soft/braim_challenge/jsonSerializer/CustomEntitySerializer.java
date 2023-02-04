package org.simbir_soft.braim_challenge.jsonSerializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.simbir_soft.braim_challenge.domain.BaseEntity;

import java.io.IOException;

public class CustomEntitySerializer extends StdSerializer<BaseEntity> {

    public CustomEntitySerializer() {
        this(null);
    }

    public CustomEntitySerializer(Class<BaseEntity> clazz) {
        super(clazz);
    }

//    @Override
//    public void serialize(Account value,
//                          JsonGenerator gen,
//                          SerializerProvider provider) throws IOException {
//        gen.writeNumber(value.getId());
//    }

    @Override
    public void serialize(BaseEntity value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeNumber(value.getId());
    }
}

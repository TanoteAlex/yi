package com.yi.base.serialize;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.ObjectMapper.DefaultTypeResolverBuilder;
import com.fasterxml.jackson.databind.ObjectMapper.DefaultTyping;
import com.fasterxml.jackson.databind.jsontype.TypeResolverBuilder;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public class JacksonSerialization implements Serialization {
    private final ObjectMapper mapObjectMapper = makeObjectMapper();

    public JacksonSerialization() {
        initObjectMapper(mapObjectMapper);
    }

    protected ObjectMapper makeObjectMapper() {
        return new ObjectMapper();
    }

    protected void initObjectMapper(ObjectMapper objectMapper) {
        objectMapper.setSerializationInclusion(Include.NON_NULL);
        objectMapper.setVisibility(objectMapper.getSerializationConfig().getDefaultVisibilityChecker()
                .withFieldVisibility(Visibility.ANY)
                .withGetterVisibility(Visibility.NONE)
                .withSetterVisibility(Visibility.NONE)
                .withCreatorVisibility(Visibility.NONE)
        );
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, false);
        objectMapper.configure(JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN, true);
        objectMapper.addMixIn(Throwable.class, ThrowableMixIn.class);

        // type info inclusion
        TypeResolverBuilder<?> mapTyper = new DefaultTypeResolverBuilder(DefaultTyping.NON_FINAL) {
            public boolean useForType(JavaType t) {
                switch (_appliesFor) {
                    case NON_CONCRETE_AND_ARRAYS:
                        while (t.isArrayType()) {
                            t = t.getContentType();
                        }
                        // fall through

                    case OBJECT_AND_NON_CONCRETE:
                        return (t.getRawClass() == Object.class) || !t.isConcrete();

                    case NON_FINAL:
                        while (t.isArrayType()) {
                            t = t.getContentType();
                        }
                        // to fix problem with wrong long to int conversion
                        if (t.getRawClass() == Long.class) {
                            return true;
                        }
                        return !t.isFinal(); // includes Object.class

                    default:
                        // case JAVA_LANG_OBJECT:
                        return (t.getRawClass() == Object.class);
                }
            }
        };

        mapTyper.init(JsonTypeInfo.Id.CLASS, null);
        mapTyper.inclusion(JsonTypeInfo.As.PROPERTY);
        mapObjectMapper.setDefaultTyping(mapTyper);
    }

    @Override
    public byte[] serialize(Object object) {
        try {
            byte[] bytes = mapObjectMapper.writeValueAsBytes(object);
            return bytes;

        } catch (JsonProcessingException ex) {
            throw new RuntimeException("对象序列化失败", ex);
        }
    }

    @Override
    public Object deserialize(byte[] bytes) {
        try {
            Object object = mapObjectMapper.readValue(new ByteArrayInputStream(bytes), Object.class);
            return object;

        } catch (IOException ex) {
            throw new RuntimeException("对象反序列化失败", ex);
        }
    }

    @JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
    @JsonAutoDetect(fieldVisibility = Visibility.NONE, getterVisibility = Visibility.PUBLIC_ONLY, setterVisibility = Visibility.PUBLIC_ONLY, isGetterVisibility = Visibility.PUBLIC_ONLY)
    public static class ThrowableMixIn {
    }
}

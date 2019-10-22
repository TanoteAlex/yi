package com.yi.base.serialize;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * 对象序列化和反序列化工具类, 默认采用 Kryo 序列化方式
 */
public final class SerializeUtils {
    private final static Map<String, Serialization> serializations = Maps.newConcurrentMap();
    private final static Serialization serialization = getSerialization("kryo");

    private SerializeUtils() {
    }

    /**
     * 获取某种序列化器的唯一实例
     *
     * @param type
     * @return
     */
    public static Serialization getSerialization(String type) {
        type = StringUtils.lowerCase(type);
        Serialization serialization = serializations.get(type);
        if (serialization != null) {
            return serialization;
        }

        synchronized (serializations) {
            serialization = serializations.get(type);
            if (serialization != null) {
                return serialization;
            }

            if (StringUtils.equals(type, "kryo")) {
                serialization = new KryoSerialization();

            } else if (StringUtils.equals(type, "jackson")) {
                serialization = new JacksonSerialization();

            } else if (StringUtils.equals(type, "java")) {
                serialization = new JavaSerialization();

            } else {
                serialization = new KryoSerialization();
            }

            serializations.put(type, serialization);
        }
        return serialization;
    }

    /**
     * 将给定的对象序列化为字节数组
     *
     * @param object
     * @return
     */
    public static byte[] serialize(Object object) {
        return serialization.serialize(object);
    }

    /**
     * 将给定的序列化字节数组反序列化成对象
     *
     * @param bytes
     * @return
     */
    public static Object deserialize(byte[] bytes) {
        return serialization.deserialize(bytes);
    }
}

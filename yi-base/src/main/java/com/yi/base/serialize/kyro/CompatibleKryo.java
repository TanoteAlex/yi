package com.yi.base.serialize.kyro;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.serializers.JavaSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Kryo 兼容性完善类，对于没有默认构造方法的类使用JAVA原始的序列化方法
 */
public class CompatibleKryo extends Kryo {
    private static final Logger logger = LoggerFactory.getLogger(CompatibleKryo.class);

    @Override
    public Serializer getDefaultSerializer(Class clazz) {
        if (clazz == null) {
            throw new IllegalArgumentException("clazz cannot be null.");
        }

        if (!clazz.isArray() && !checkZeroArgConstructor(clazz)) {
            if (logger.isWarnEnabled()) {
                logger.warn(clazz + " has no zero-arg constructor and this will affect the serialization performance");
            }
            return new JavaSerializer();
        }

        return super.getDefaultSerializer(clazz);
    }

    /**
     * 检查类是否有无参数的默认构造方法
     *
     * @param clazz
     * @return
     */
    private boolean checkZeroArgConstructor(Class clazz) {
        try {
            return clazz.getDeclaredConstructor() != null;

        } catch (NoSuchMethodException ex) {
            return false;
        }
    }
}
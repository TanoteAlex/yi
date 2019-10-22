package com.yi.base.serialize;

/**
 * 对象序列化和反序列化接口定义
 */
public interface Serialization {
    /**
     * 将给定的对象序列化为字节数组
     *
     * @param object
     * @return
     */
    byte[] serialize(Object object);

    /**
     * 将给定的序列化字节数组反序列化成对象
     *
     * @param bytes
     * @return
     */
    Object deserialize(byte[] bytes);
}

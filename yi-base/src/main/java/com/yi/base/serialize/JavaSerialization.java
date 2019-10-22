package com.yi.base.serialize;

import java.io.*;

/**
 * 基于 Kryo 的对象序列化和反序列化工具类
 */
public class JavaSerialization implements Serialization {
    @Override
    public byte[] serialize(Object object) {
        if (object == null) {
            return null;
        }

        try {
            ByteArrayOutputStream result = new ByteArrayOutputStream();
            ObjectOutputStream outputStream = new ObjectOutputStream(result);
            outputStream.writeObject(object);
            outputStream.close();

            byte[] bytes = result.toByteArray();
            return bytes;

        } catch (IOException ex) {
            throw new RuntimeException("对象序列化失败", ex);
        }
    }

    @Override
    public Object deserialize(byte[] bytes) {
        if (bytes == null) {
            return null;
        }

        try {
            ObjectInputStream inputStream = new ObjectInputStream(new ByteArrayInputStream(bytes));
            Object object = inputStream.readObject();
            return object;

        } catch (IOException | ClassNotFoundException ex) {
            throw new RuntimeException("对象反序列化失败", ex);
        }
    }
}

package com.yi.base.serialize;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.yi.base.serialize.kyro.KryoFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * 基于 Kryo 的对象序列化和反序列化工具类
 */
public class KryoSerialization implements Serialization {
    @Override
    public byte[] serialize(Object object) {
        if (object == null) {
            return null;
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream(2048);
        Output output = new Output(out);

        KryoFactory kryoFactory = KryoFactory.getDefaultFactory();
        Kryo kryo = kryoFactory.getKryo();

        try {
            kryo.writeClassAndObject(output, object);
            output.close();

        } finally {
            kryoFactory.returnKryo(kryo);
        }

        byte[] bytes = out.toByteArray();
        return bytes;
    }

    @Override
    public Object deserialize(byte[] bytes) {
        if (bytes == null) {
            return null;
        }

        ByteArrayInputStream in = new ByteArrayInputStream(bytes);
        Input input = new Input(in);

        KryoFactory kryoFactory = KryoFactory.getDefaultFactory();
        Kryo kryo = kryoFactory.getKryo();

        Object object;
        try {
            object = kryo.readClassAndObject(input);
            input.close();

        } finally {
            kryoFactory.returnKryo(kryo);
        }

        return object;
    }
}

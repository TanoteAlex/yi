package com.yi.base.serialize.kyro;

import com.esotericsoftware.kryo.Kryo;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 使用队列缓存Kryo实例的Kryo工厂类
 */
public class PooledKryoFactory extends KryoFactory {
    private final Queue<Kryo> pool = new ConcurrentLinkedQueue<Kryo>();

    @Override
    public void returnKryo(Kryo kryo) {
        pool.offer(kryo);
    }

    @Override
    public Kryo getKryo() {
        Kryo kryo = pool.poll();
        if (kryo == null) {
            kryo = createKryo();
        }
        return kryo;
    }

    @Override
    public void close() {
        pool.clear();
    }
}
package com.yi.base.serialize.kyro;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.serializers.DefaultSerializers;
import de.javakaffee.kryoserializers.*;
import de.javakaffee.kryoserializers.cglib.CGLibProxySerializer;
import de.javakaffee.kryoserializers.guava.*;
import de.javakaffee.kryoserializers.jodatime.JodaLocalDateSerializer;
import de.javakaffee.kryoserializers.jodatime.JodaLocalDateTimeSerializer;

import java.lang.reflect.InvocationHandler;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

/**
 * Kryo工厂类, getKryo 获取一个实例, returnKryo 返还实例（返还后实例不可以再使用）
 */
public abstract class KryoFactory {
    private static final KryoFactory factory = new PooledKryoFactory();

    private final Set<Class> registrations = new LinkedHashSet<Class>();
    private boolean registrationRequired = false;
    private volatile boolean kryoCreated = false;

    protected KryoFactory() {
    }

    /**
     * 获取默认的KyroFactory
     *
     * @return
     */
    public static KryoFactory getDefaultFactory() {
        return factory;
    }

    /**
     * 获取一个Kyro实例，注意处理完成后必须调用 returnKyro 返回给KyroFactory
     * <p/>
     * 原因就是 Kyro 不是线程安全的
     *
     * @return
     */
    public abstract Kryo getKryo();

    /**
     * 返回一个Kyro实例到KyroFactory，可以用于其他方法
     *
     * @param kryo
     */
    public abstract void returnKryo(Kryo kryo);

    /**
     * 关闭KyroFactory，释放相关资源
     */
    public void close() {
        // do nothing by default
    }

    /**
     * only supposed to be called at startup time
     * <p/>
     * later may consider adding support for custom serializer, custom id, etc
     */
    public void registerClass(Class clazz) {
        if (kryoCreated) {
            throw new IllegalStateException("Can't register class after creating kryo instance");
        }
        registrations.add(clazz);
    }

    /**
     * 创建一个Kyro实例
     *
     * @return
     */
    protected Kryo createKryo() {
        if (!kryoCreated) {
            kryoCreated = true;
        }

        Kryo kryo = new CompatibleKryo();

        kryo.setReferences(false);
        kryo.setRegistrationRequired(registrationRequired);
        kryo.getFieldSerializerConfig().setOptimizedGenerics(true);

        kryo.register(Arrays.asList("").getClass(), new ArraysAsListSerializer());
        kryo.register(GregorianCalendar.class, new GregorianCalendarSerializer());
        kryo.register(InvocationHandler.class, new JdkProxySerializer());
        kryo.register(BigDecimal.class, new DefaultSerializers.BigDecimalSerializer());
        kryo.register(BigInteger.class, new DefaultSerializers.BigIntegerSerializer());
        kryo.register(Pattern.class, new RegexSerializer());
        kryo.register(BitSet.class, new BitSetSerializer());
        kryo.register(URI.class, new URISerializer());

        UnmodifiableCollectionsSerializer.registerSerializers(kryo);
        SynchronizedCollectionsSerializer.registerSerializers(kryo);
        SubListSerializers.addDefaultSerializers(kryo);

        // register CGLibProxySerializer, works in combination with the appropriate action in handleUnregisteredClass (see below)
        kryo.register(CGLibProxySerializer.CGLibProxyMarker.class, new CGLibProxySerializer());

        // joda DateTime, LocalDate and LocalDateTime
        kryo.register(LocalDate.class, new JodaLocalDateSerializer());
        kryo.register(LocalDateTime.class, new JodaLocalDateTimeSerializer());

        // protobuf
        // kryo.register(SampleProtoA.class, new ProtobufSerializer()); // or override Kryo.getDefaultSerializer as shown below

        // wicket
        // kryo.register(MiniMap.class, new MiniMapSerializer());

        // guava ImmutableList, ImmutableSet, ImmutableMap, ImmutableMultimap
        ImmutableListSerializer.registerSerializers(kryo);
        ImmutableSetSerializer.registerSerializers(kryo);
        ImmutableSortedSetSerializer.registerSerializers(kryo);
        ImmutableMapSerializer.registerSerializers(kryo);
        ImmutableMultimapSerializer.registerSerializers(kryo);

        // now just added some very common classes
        kryo.register(HashMap.class);
        kryo.register(ArrayList.class);
        kryo.register(LinkedList.class);
        kryo.register(HashSet.class);
        kryo.register(TreeSet.class);
        kryo.register(Hashtable.class);
        kryo.register(Date.class);
        kryo.register(Calendar.class);
        kryo.register(ConcurrentHashMap.class);
        kryo.register(SimpleDateFormat.class);
        kryo.register(GregorianCalendar.class);
        kryo.register(Vector.class);
        kryo.register(BitSet.class);
        kryo.register(StringBuffer.class);
        kryo.register(StringBuilder.class);
        kryo.register(Object.class);
        kryo.register(Object[].class);
        kryo.register(String[].class);
        kryo.register(byte[].class);
        kryo.register(char[].class);
        kryo.register(int[].class);
        kryo.register(float[].class);
        kryo.register(double[].class);

        for (Class clazz : registrations) {
            kryo.register(clazz);
        }

        return kryo;
    }

    public void setRegistrationRequired(boolean registrationRequired) {
        this.registrationRequired = registrationRequired;
    }
}

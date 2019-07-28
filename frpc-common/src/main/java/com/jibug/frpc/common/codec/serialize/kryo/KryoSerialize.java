package com.jibug.frpc.common.codec.serialize.kryo;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.FastInput;
import com.esotericsoftware.kryo.io.FastOutput;
import com.esotericsoftware.kryo.io.KryoObjectInput;
import com.esotericsoftware.kryo.io.KryoObjectOutput;
import com.jibug.frpc.common.codec.serialize.Serialize;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * kryo 线程不安全
 *
 * @author heyingcai
 * @date 2019-03-29 14:53
 */
public class KryoSerialize implements Serialize {

    private static final ThreadLocal<Kryo> KRYOS_MAP = ThreadLocal.withInitial(() -> new Kryo());

    @Override
    public byte[] serialize(Object object) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        Kryo kryo = KRYOS_MAP.get();
        KryoObjectOutput out = new KryoObjectOutput(kryo, new FastOutput(bos));
        out.writeObject(object);
        out.flush();
        return bos.toByteArray();
    }

    @Override
    public <T> T deserialize(InputStream inputStream, byte[] bytes, Class<T> clazz) throws IOException, ClassNotFoundException {
        Kryo kryo = KRYOS_MAP.get();
        kryo.register(clazz);
        KryoObjectInput input = new KryoObjectInput(kryo, new FastInput(new ByteArrayInputStream(bytes)));
        return (T) input.readObject();
    }
}

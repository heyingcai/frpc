package com.jibug.frpc.common.codec.serialize.jdk;

import com.jibug.frpc.common.codec.serialize.Serialize;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * @author heyingcai
 * @date 2019-07-29 17:16
 */
public class JdkSerialize implements Serialize {

    @Override
    public byte[] serialize(Object object) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream os = new ObjectOutputStream(bos);
        os.writeObject(object);
        os.flush();
        os.close();
        return bos.toByteArray();
    }

    @Override
    public <T> T deserialize(byte[] bytes, Class<T> clazz) throws IOException, ClassNotFoundException {
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        ObjectInputStream oi = new ObjectInputStream(bis);
        return (T) oi.readObject();
    }
}

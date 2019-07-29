package com.jibug.frpc.common.codec.serialize;

import java.io.IOException;

/**
 * @author heyingcai
 */
public interface Serialize {

    /**
     * 序列化
     *
     * @param object
     * @return
     * @throws IOException
     */
    byte[] serialize(Object object) throws IOException;

    /**
     * 反序列化
     *
     * @param bytes
     * @param clazz
     * @param <T>
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    <T> T deserialize(byte[] bytes, Class<T> clazz) throws IOException, ClassNotFoundException;

}

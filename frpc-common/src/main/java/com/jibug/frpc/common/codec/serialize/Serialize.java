package com.jibug.frpc.common.codec.serialize;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author heyingcai
 */
public interface Serialize {

    /**
     * 序列化
     *
     * @param outputStream
     * @param object
     * @return
     * @throws IOException
     */
    void serialize(OutputStream outputStream, Object object) throws IOException;

    /**
     * 反序列化
     *
     * @param inputStream
     * @param bytes
     * @param clazz
     * @param <T>
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    <T> T deserialize(InputStream inputStream, byte[] bytes, Class<T> clazz) throws IOException, ClassNotFoundException;

}

package com.jibug.frpc.core.common.codec.serialize;

import java.io.IOException;

/**
 * @author heyingcai
 */
public interface Serialize {

    /**
     * @param object
     * @return
     * @throws IOException
     */
    byte[] serialize(Object object) throws IOException;

    /**
     * @param bytes
     * @param clazz
     * @param <T>
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    <T> T deserialize(byte[] bytes, Class<T> clazz) throws IOException, ClassNotFoundException;

}

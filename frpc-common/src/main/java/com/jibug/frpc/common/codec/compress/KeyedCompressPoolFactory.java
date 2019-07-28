package com.jibug.frpc.common.codec.compress;

import org.apache.commons.pool2.BaseKeyedPooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

/**
 * @author heyingcai
 */
public class KeyedCompressPoolFactory extends BaseKeyedPooledObjectFactory<Byte, Compress> {
    @Override
    public Compress create(Byte aByte) throws Exception {
        Class<?> compressClass = CompressEnum.getCompressClass(aByte);
        if (compressClass != null) {
            return (Compress) compressClass.newInstance();
        }
        return null;
    }

    @Override
    public PooledObject<Compress> wrap(Compress compress) {
        return new DefaultPooledObject<>(compress);
    }
}

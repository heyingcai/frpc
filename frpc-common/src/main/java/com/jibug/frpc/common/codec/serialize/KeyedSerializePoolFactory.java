package com.jibug.frpc.common.codec.serialize;

import org.apache.commons.pool2.BaseKeyedPooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

/**
 * @author heyingcai
 */
public class KeyedSerializePoolFactory extends BaseKeyedPooledObjectFactory<Byte, Serialize> {

    @Override
    public Serialize create(Byte key) throws Exception {
        Class<?> serializeClass = SerializeProtocolEnum.getSerialize(key);
        if (serializeClass != null) {
            return (Serialize) serializeClass.newInstance();
        }
        return null;
    }

    @Override
    public PooledObject<Serialize> wrap(Serialize serialize) {
        return new DefaultPooledObject<>(serialize);
    }
}

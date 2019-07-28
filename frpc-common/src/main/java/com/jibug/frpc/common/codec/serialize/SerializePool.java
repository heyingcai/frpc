package com.jibug.frpc.common.codec.serialize;

import com.jibug.frpc.common.util.CommonObjectPool;
import org.apache.commons.pool2.impl.GenericKeyedObjectPoolConfig;

/**
 * @author heyingcai
 */
public class SerializePool extends CommonObjectPool<Byte, Serialize> {

    private static volatile SerializePool pool = null;

    private SerializePool(GenericKeyedObjectPoolConfig config) {
        super(new KeyedSerializePoolFactory(), config);
    }

    private SerializePool() {
        GenericKeyedObjectPoolConfig config = new GenericKeyedObjectPoolConfig();
        config.setMaxTotal(500);
        config.setMinIdlePerKey(20);
        config.setMaxIdlePerKey(100);
        config.setMaxWaitMillis(6000);
        config.setMinEvictableIdleTimeMillis(600000);
        pool = new SerializePool(config);
    }

    public static SerializePool getInstance() {
        if (pool == null) {
            synchronized (Serialize.class) {
                if (pool == null) {
                    pool = new SerializePool();
                }
            }
        }
        return pool;
    }
}

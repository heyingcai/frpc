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

    public static SerializePool getInstance() {
        if (pool == null) {
            synchronized (SerializePool.class) {
                if (pool == null) {
                    GenericKeyedObjectPoolConfig config = new GenericKeyedObjectPoolConfig();
                    config.setMaxTotalPerKey(500);
                    config.setMinIdlePerKey(20);
                    config.setMaxIdlePerKey(100);
                    config.setMaxWaitMillis(6000);
                    config.setMinEvictableIdleTimeMillis(600000);
                    pool = new SerializePool(config);
                }
            }
        }
        return pool;
    }
}

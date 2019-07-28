package com.jibug.frpc.common.codec.compress;

import com.jibug.frpc.common.util.CommonObjectPool;
import org.apache.commons.pool2.impl.GenericKeyedObjectPoolConfig;

/**
 * @author heyingcai
 */
public class CompressPool extends CommonObjectPool<Byte, Compress> {

    private static volatile CompressPool pool = null;

    private CompressPool(GenericKeyedObjectPoolConfig config) {
        super(new KeyedCompressPoolFactory(), config);
    }

    private CompressPool() {
        GenericKeyedObjectPoolConfig config = new GenericKeyedObjectPoolConfig();
        config.setMaxTotal(500);
        config.setMinIdlePerKey(20);
        config.setMaxIdlePerKey(100);
        config.setMaxWaitMillis(6000);
        config.setMinEvictableIdleTimeMillis(600000);
        pool = new CompressPool(config);
    }

    public static CompressPool getInstance() {
        if (pool == null) {
            synchronized (CompressPool.class) {
                if (pool == null) {
                    pool = new CompressPool();
                }
            }
        }
        return pool;
    }

}

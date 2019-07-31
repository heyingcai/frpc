package com.jibug.frpc.net;

import com.jibug.frpc.common.util.CommonObjectPool;
import org.apache.commons.pool2.impl.GenericKeyedObjectPoolConfig;

/**
 * @author heyingcai
 */
public class NettyConnectionPool extends CommonObjectPool<String, Connection> {

    private static volatile NettyConnectionPool pool = null;

    private NettyConnectionPool(GenericKeyedObjectPoolConfig config) {
        super(new KeyedConnectionPoolFactory(), config);
    }

    public static NettyConnectionPool getInstance() {
        if (pool == null) {
            synchronized (NettyConnectionPool.class) {
                if (pool == null) {
                    GenericKeyedObjectPoolConfig config = new GenericKeyedObjectPoolConfig();
                    config.setMaxTotal(100000);
                    config.setMinIdlePerKey(20);
                    config.setMaxIdlePerKey(100);
                    config.setMaxWaitMillis(60000);
                    config.setMinEvictableIdleTimeMillis(600000);
                    pool = new NettyConnectionPool(config);
                }
            }
        }
        return pool;
    }

}

package com.jibug.frpc.common.util;

import org.apache.commons.pool2.BaseKeyedPooledObjectFactory;
import org.apache.commons.pool2.impl.GenericKeyedObjectPool;
import org.apache.commons.pool2.impl.GenericKeyedObjectPoolConfig;

/**
 * @author heyingcai
 */
public class CommonObjectPool<K, V> {

    public CommonObjectPool() {
    }

    private GenericKeyedObjectPool<K, V> genericKeyedObjectPool;

    public CommonObjectPool(BaseKeyedPooledObjectFactory<K, V> baseFactory, GenericKeyedObjectPoolConfig config) {
        this.genericKeyedObjectPool = new GenericKeyedObjectPool<>(baseFactory, config);
    }

    public V getObject(K k) {
        try {
            return genericKeyedObjectPool.borrowObject(k);
        } catch (Exception e) {
            return null;
        }
    }

    public void restore(final K k, final V v) {
        genericKeyedObjectPool.returnObject(k, v);
    }

    public void clear(K k) {
        genericKeyedObjectPool.clear(k);
    }

}

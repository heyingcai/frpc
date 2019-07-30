package com.jibug.frpc.net;

import org.apache.commons.pool2.BaseKeyedPooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

/**
 * @author heyingcai
 */
public class KeyedConnectionPoolFactory extends BaseKeyedPooledObjectFactory<String, Connection> {

    private ConnectionFactory connectionFactory;

    public KeyedConnectionPoolFactory() {
    }

    public KeyedConnectionPoolFactory(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Override
    public Connection create(String address) throws Exception {
        Connection connect = connectionFactory.connect(address);
        return connect;
    }

    @Override
    public PooledObject<Connection> wrap(Connection connection) {
        return new DefaultPooledObject<>(connection);
    }

    @Override
    public void destroyObject(String key, PooledObject<Connection> p) throws Exception {
        p.getObject().close();
    }

    @Override
    public boolean validateObject(String key, PooledObject<Connection> p) {
        Connection connection = p.getObject();
        return connection.getChannel().isOpen() && connection.isActive();
    }
}

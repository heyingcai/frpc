package com.jibug.frpc.net;

import org.apache.commons.pool2.BaseKeyedPooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author heyingcai
 */
public class KeyedConnectionPoolFactory extends BaseKeyedPooledObjectFactory<String, Connection> {

    private Map<String, ConnectionFactory> connectionFactoryMap = new ConcurrentHashMap<>();

    private ConnectionFactory connectionFactory;

    public KeyedConnectionPoolFactory() {
    }

    public KeyedConnectionPoolFactory(ConnectionFactory connectionFactory) {

        this.connectionFactory = connectionFactory;
    }

    @Override
    public Connection create(String address) throws Exception {
        ConnectionFactory connectionFactory = connectionFactoryMap.get(address);
        if (connectionFactory == null) {
            connectionFactory = new NettyClient();
            connectionFactoryMap.put(address, connectionFactory);
        }
        Connection connect = this.connectionFactory.connect(address);
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

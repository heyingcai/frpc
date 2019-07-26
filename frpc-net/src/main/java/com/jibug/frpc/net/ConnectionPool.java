package com.jibug.frpc.net;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author heyingcai
 */
public class ConnectionPool implements Pool<Connection>{

    private CopyOnWriteArrayList<Connection> container = new CopyOnWriteArrayList<>();


    @Override
    public void add(Connection connection) {

    }

    @Override
    public Connection get() {
        return null;
    }

    @Override
    public List<Connection> getAll() {
        return null;
    }

    @Override
    public int size() {
        return container.size();
    }

    @Override
    public boolean isEmpty() {
        return container.isEmpty();
    }

    @Override
    public boolean contains(Connection connection) {
        return container.contains(connection);
    }

    @Override
    public void remove(Connection connection) {

    }
}

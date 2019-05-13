package com.jibug.frpc.net;

import java.util.List;

/**
 * @author heyingcai
 */
public interface ConnectionManager {

    void init();

    void add(Connection connection);

    Connection get();

    List<Connection> getAll();

    void remove(Connection connection);

    void removeAll();

    Connection create(String address);

    Connection create(String ip, int port);
}

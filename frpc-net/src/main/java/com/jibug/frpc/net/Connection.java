package com.jibug.frpc.net;

import java.io.Closeable;
import java.io.IOException;
import java.nio.channels.Channel;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author heyingcai
 */
public class Connection implements Closeable {

    private Channel channel;

    private AtomicBoolean closed = new AtomicBoolean(false);

    private final ConcurrentHashMap<String, Object> attributes = new ConcurrentHashMap<>();


    @Override
    public void close() throws IOException {
        channel.close();
    }
}

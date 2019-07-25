package com.jibug.frpc.net;

import io.netty.channel.Channel;

import java.io.Closeable;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author heyingcai
 */
public class Connection implements Closeable {

    private Channel channel;

    private final ConcurrentHashMap<String, Object> attributes = new ConcurrentHashMap<>();

    public Connection() {
    }

    public Connection(Channel channel) {
        this.channel = channel;
    }

    @Override
    public void close() throws IOException {
        channel.close();
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }
}

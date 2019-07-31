package com.jibug.frpc.net;

import com.jibug.frpc.common.model.Invoker;
import io.netty.channel.Channel;
import io.netty.util.AttributeKey;

import java.io.Closeable;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author heyingcai
 */
public class Connection implements Closeable {

    private Channel channel;

    public static final AttributeKey<Connection> CONNECTION = AttributeKey.valueOf("connection");

    private final Map<Long, Invoker> invokerMap = new ConcurrentHashMap<>();

    private final ConcurrentHashMap<String, Object> attributes = new ConcurrentHashMap<>();

    public Connection() {
    }

    public Connection(Channel channel) {
        this.channel = channel;
        this.channel.attr(CONNECTION).set(this);
    }

    @Override
    public void close() throws IOException {
        if (channel != null) {
            channel.close();
        }
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public boolean isActive() {
        return channel != null && channel.isActive();
    }
}

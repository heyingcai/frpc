package com.jibug.frpc.net.client;

import com.jibug.frpc.net.NamedThreadFactory;
import com.jibug.frpc.net.handler.RpcClientHandler;
import com.jibug.frpc.net.handler.RpcDecoder;
import com.jibug.frpc.net.handler.RpcEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author heyingcai
 */
public class NettyConnectionFactory extends ConnectionFactory {

    private Bootstrap bootstrap;

    private static final EventLoopGroup workerGroup = new NioEventLoopGroup(
            Runtime
                    .getRuntime()
                    .availableProcessors() + 1,
            new NamedThreadFactory(
                    "Rpc-netty-client-worker",
                    true));

    @Override
    public void init() {
        bootstrap = new Bootstrap();
        bootstrap.group(workerGroup)
                .option(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        initClientChannel(ch);
                    }
                });
    }

    public void initClientChannel(SocketChannel ch) {
        ch.pipeline().addLast("encode", new RpcEncoder());
        ch.pipeline().addLast("decode", new RpcDecoder());
        ch.pipeline().addLast("handler", new RpcClientHandler());
    }

    @Override
    public void connect() {

    }
}

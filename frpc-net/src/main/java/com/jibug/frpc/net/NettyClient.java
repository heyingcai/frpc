package com.jibug.frpc.net;

import com.jibug.frpc.common.exception.FrpRuntimeException;
import com.jibug.frpc.net.handler.HeartbeatHandler;
import com.jibug.frpc.net.handler.ClientProcessHandler;
import com.jibug.frpc.net.handler.RpcDecoder;
import com.jibug.frpc.net.handler.RpcEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

/**
 * @author heyingcai
 */
public class NettyClient extends ConnectionFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(NettyClient.class);

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
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 1000)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        initClientChannel(ch);
                    }
                });
    }

    public void initClientChannel(SocketChannel ch) {
        ch.pipeline().addLast("encoder", new RpcEncoder());
        ch.pipeline().addLast("decoder", new RpcDecoder());

        ch.pipeline().addLast("idleStateHandler", new IdleStateHandler(15000, 150000, 0, TimeUnit.MILLISECONDS));
        ch.pipeline().addLast("heartbeatHandler", new HeartbeatHandler());
        ch.pipeline().addLast("handler", new ClientProcessHandler());
    }

    @Override
    public Connection connect(String ip, int port) {
        ChannelFuture future = bootstrap.connect(new InetSocketAddress(ip, port));
        future.awaitUninterruptibly();
        String addr = ip + ":" + port;
        if (!future.isDone()) {
            String errMsg = "Connection to " + addr + " timeout!";
            LOGGER.warn(errMsg);
            throw new FrpRuntimeException(errMsg);
        }
        if (future.isCancelled()) {
            String errMsg = "Connection to " + addr + " cancelled!";
            LOGGER.warn(errMsg);
            throw new FrpRuntimeException(errMsg);
        }
        if (!future.isSuccess()) {
            String errMsg = "Connection to " + addr + " error!";
            LOGGER.warn(errMsg);
            throw new FrpRuntimeException(errMsg, future.cause());
        }
        return new Connection(future.channel());
    }

    @Override
    public Connection connect(String address) {
        String[] strings = address.split(":");
        return connect(strings[0], Integer.parseInt(strings[1]));
    }
}

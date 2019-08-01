package com.jibug.frpc.net;

import com.jibug.frpc.common.config.ServerConfig;
import com.jibug.frpc.net.handler.RpcDecoder;
import com.jibug.frpc.net.handler.RpcEncoder;
import com.jibug.frpc.net.handler.ServerIdleHander;
import com.jibug.frpc.net.handler.ServerProcessHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import org.apache.commons.lang3.StringUtils;

import java.net.InetSocketAddress;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author heyingcai
 */
public class NettyServer extends AbstractServer {

    private ServerConfig serverConfig;

    private ServerBootstrap serverBootstrap;

    private ChannelFuture channelFuture;

    private EventLoopGroup bossGroup = new NioEventLoopGroup(1, new NamedThreadFactory("Rpc-netty-server-boss", false));

    private EventLoopGroup workGroup = new NioEventLoopGroup(Runtime.getRuntime().availableProcessors() * 2, new NamedThreadFactory("Rpc-netty-server-worker", true));

    public NettyServer(ServerConfig serverConfig) {
        this.serverConfig = serverConfig;
    }

    @Override
    public void doInit() {
        serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(bossGroup, workGroup).channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 1024)
                .option(ChannelOption.SO_REUSEADDR, true)
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childHandler(newChannelInitializer());
    }

    public ChannelInitializer<SocketChannel> newChannelInitializer() {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(serverConfig.getThreadPoolCore()
                , serverConfig.getThreadPoolMax(), serverConfig.getThreadKeepAliveTime(), TimeUnit.MILLISECONDS
                , serverConfig.getThreadQueueSize() > 0 ? new LinkedBlockingQueue<>(
                serverConfig.getThreadQueueSize()) : new SynchronousQueue<>(), new NamedThreadFactory("RPC-SERVER", true));
        return new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline().addLast("decoder", new RpcDecoder());
                ch.pipeline().addLast("encoder", new RpcEncoder());
                ch.pipeline().addLast("idleStateHandler", new IdleStateHandler(0, 0, 150000, TimeUnit.MILLISECONDS));
                ch.pipeline().addLast("idleHandler", new ServerIdleHander());
                ch.pipeline().addLast("processHandler", new ServerProcessHandler(threadPoolExecutor));
            }
        };

    }

    @Override
    public void doStart() throws InterruptedException {
        String host = serverConfig.getHost();
        int port = serverConfig.getPort();
        if (StringUtils.isNotBlank(host)) {
            channelFuture = serverBootstrap.bind(new InetSocketAddress(host, port)).sync();
        } else {
            channelFuture = this.serverBootstrap.bind(new InetSocketAddress(port)).sync();
        }
        channelFuture.channel().closeFuture();
    }

    @Override
    public void doStop() {
        if (channelFuture != null) {
            channelFuture.channel().close();
        }
    }


}

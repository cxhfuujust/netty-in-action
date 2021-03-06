package com.chenx.netty.example.server;

import com.chenx.netty.example.server.codec.OrderFrameDecoder;
import com.chenx.netty.example.server.codec.OrderFrameEncoder;
import com.chenx.netty.example.server.codec.OrderProtocolDecoder;
import com.chenx.netty.example.server.codec.OrderProtocolEncoder;
import com.chenx.netty.example.server.codec.handler.OrderServerProcessHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioChannelOption;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.concurrent.DefaultThreadFactory;

import java.util.concurrent.ExecutionException;

public class Server {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ServerBootstrap serverBootstrap = new ServerBootstrap();

        NioEventLoopGroup boss = new NioEventLoopGroup(0, new DefaultThreadFactory("boss"));
        NioEventLoopGroup worker = new NioEventLoopGroup(0, new DefaultThreadFactory("worker"));
        serverBootstrap.channel(NioServerSocketChannel.class)  //1.设置IO模式
                .handler(new LoggingHandler(LogLevel.INFO))
                .group(boss, worker) //2.设置reactor方式
                // 这是两个必调的两个参数
                .childOption(NioChannelOption.TCP_NODELAY, true)
                .childOption(ChannelOption.RCVBUF_ALLOCATOR, new AdaptiveRecvByteBufAllocator())
                .childOption(ChannelOption.ALLOCATOR, ByteBufAllocator.DEFAULT)

                .option(NioChannelOption.SO_BACKLOG, 1024)

                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast("OrderFrameDecoder", new OrderFrameDecoder());
                        pipeline.addLast(new OrderFrameEncoder());
                        pipeline.addLast(new OrderProtocolEncoder());
                        pipeline.addLast(new OrderProtocolDecoder());

                        pipeline.addLast(new LoggingHandler(LogLevel.INFO));

                        pipeline.addLast(new OrderServerProcessHandler());
                    }
                });

        ChannelFuture channelFuture = serverBootstrap.bind(8090).sync();
        channelFuture.channel().closeFuture().get();
    }
}

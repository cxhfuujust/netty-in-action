package com.chenx.netty.example.client;

import com.chenx.netty.example.client.codec.*;
import com.chenx.netty.example.client.codec.dispatcher.OperationResultFuture;
import com.chenx.netty.example.client.codec.dispatcher.RequestPendingCenter;
import com.chenx.netty.example.client.codec.dispatcher.ResponseDispatcherHandler;
import com.chenx.netty.example.common.OperationResult;
import com.chenx.netty.example.common.RequestMessage;
import com.chenx.netty.example.common.order.OrderOperation;
import com.chenx.netty.example.util.IdUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioChannelOption;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.util.concurrent.ExecutionException;

/**
 *
 */
public class ClientV2 {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        final RequestPendingCenter requestPendingCenter = new RequestPendingCenter();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.channel(NioSocketChannel.class)  //1.设置IO模式
                .group(new NioEventLoopGroup()) //2.设置reactor方式
                .option(NioChannelOption.CONNECT_TIMEOUT_MILLIS, 10 * 1000) //设置client连接超时时间
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new OrderFrameDecoder());
                        pipeline.addLast(new OrderFrameEncoder());
                        pipeline.addLast(new OrderProtocolEncoder());
                        pipeline.addLast(new OrderProtocolDecoder());

                        pipeline.addLast(new ResponseDispatcherHandler(requestPendingCenter));
                        //第一级的codec,所以放在最小面。//对比Client区别
                        pipeline.addLast(new OperationToRequestMessageEncoder());

                        pipeline.addLast(new LoggingHandler(LogLevel.INFO));
                    }
                });
        //1.连接到服务端
        ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 8090);
        channelFuture.sync();

        long streamId = IdUtil.nextId();
        RequestMessage requestMessage = new RequestMessage(
                streamId, new OrderOperation(1001, "tudou"));

        OperationResultFuture operationResultFuture = new OperationResultFuture();
        requestPendingCenter.add(streamId, operationResultFuture);

        channelFuture.channel().writeAndFlush(requestMessage);

        OperationResult operationResult = operationResultFuture.get();
        System.out.println("结果: "+operationResult);

        channelFuture.channel().closeFuture().get();
    }
}

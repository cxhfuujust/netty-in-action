package com.chenx.netty.example.client;

import com.chenx.netty.example.client.codec.*;
import com.chenx.netty.example.common.RequestMessage;
import com.chenx.netty.example.common.order.OrderOperation;
import com.chenx.netty.example.util.IdUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.util.concurrent.ExecutionException;

/**
 * 添加了 OperationToRequestMessageEncoder 类型转换处理
 */
public class ClientV1 {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.channel(NioSocketChannel.class)  //1.设置IO模式
                .group(new NioEventLoopGroup()) //2.设置reactor方式
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new OrderFrameDecoder());
                        pipeline.addLast(new OrderFrameEncoder());
                        pipeline.addLast(new OrderProtocolEncoder());
                        pipeline.addLast(new OrderProtocolDecoder());

                        //第一级的codec,所以放在最小面。//对比Client区别
                        pipeline.addLast(new OperationToRequestMessageEncoder());

                        pipeline.addLast(new LoggingHandler(LogLevel.INFO));
                    }
                });
        //1.连接到服务端
        ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 8090);
        channelFuture.sync();
        //2.把数据发送到服务端
        OrderOperation operation = new OrderOperation(1001, "tudou");
        channelFuture.channel().writeAndFlush(operation);

        //RequestMessage也是可以发送出去的，netty每经过一个handler处理，都会做类型判断，不是其输入类跳过
        RequestMessage requestMessage = new RequestMessage(IdUtil.nextId(), new OrderOperation(1001, "tudou"));
        channelFuture.channel().writeAndFlush(requestMessage);


        channelFuture.channel().closeFuture().get();
    }
}

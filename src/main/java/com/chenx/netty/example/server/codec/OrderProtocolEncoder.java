package com.chenx.netty.example.server.codec;

import com.chenx.netty.example.common.ResponseMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

/**
 * 3.***ProtocolEncoder：要把业务对象发出去之前，要又两个编码要做，不可能把业务对象直接发出去，需要把业务对象转成
 * 二进制，所以第一步把业务对象转成ByteBuf，第二步，为了对方(客户端)解决半包和沾包问题，需要***FrameEncoder。
 */
public class OrderProtocolEncoder extends MessageToMessageEncoder<ResponseMessage> {

    /**
     * ResponseMessage 转成了 ByteBuf
     */
    @Override
    protected void encode(ChannelHandlerContext ctx, ResponseMessage responseMessage, List<Object> out) throws Exception {
        ByteBuf buffer = ctx.alloc().buffer();
        responseMessage.encode(buffer);

        out.add(buffer);
    }
}

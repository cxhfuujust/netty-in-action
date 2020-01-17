package com.chenx.netty.example.client.codec;

import com.chenx.netty.example.common.RequestMessage;
import com.chenx.netty.example.common.ResponseMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

/**
 * 第四步：ByteBuf转化成对象。案例是把 ByteBuf 解码出我们要的结果 ResponseMessage
 */
public class OrderProtocolDecoder extends MessageToMessageDecoder<ByteBuf> {

    /**
     * 把 ByteBuf 转换成 RequestMessage,转成 RequestMessage 后就可以用 handler 处理了
     * @param ctx
     * @param msg
     * @param out
     * @throws Exception
     */
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.decode(msg);
        //加入到out中，这样才能传递出去
        out.add(responseMessage);
    }
}

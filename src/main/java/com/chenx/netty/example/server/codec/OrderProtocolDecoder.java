package com.chenx.netty.example.server.codec;

import com.chenx.netty.example.common.RequestMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

/**
 * 2.***ProtocolDecoder：解出了ByteBuf之后，为了给业务层做处理，还需要第二层的decode，把 ByteBuf 转换成 业务对象。
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
        RequestMessage requestMessage = new RequestMessage();
        requestMessage.decode(msg);
        //加入到out中，这样才能传递出去
        out.add(requestMessage);
    }
}

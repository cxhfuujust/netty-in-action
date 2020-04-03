package com.chenx.netty.example.server.codec.handler;

import com.chenx.netty.example.common.Operation;
import com.chenx.netty.example.common.OperationResult;
import com.chenx.netty.example.common.RequestMessage;
import com.chenx.netty.example.common.ResponseMessage;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * SimpleChannelInboundHandler 相对于 ChannelInboundHandlerAdapter 的好处是帮我们释放了ByteBuf
 */
@ChannelHandler.Sharable
public class OrderServerProcessHandler extends SimpleChannelInboundHandler<RequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RequestMessage requestMessage) throws Exception {
        Operation operation = requestMessage.getMessageBody();
        OperationResult operationResult = operation.execute();

        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setMessageHeader(requestMessage.getMessageHeader());
        responseMessage.setMessageBody(operationResult);

        //自己写出去，不是out传出去的那种方式，对于把数据写出去，还需要两个encode
        ctx.writeAndFlush(responseMessage);

        // 这种会把这个消息在pipeline中重新走一边，可能会产生消息处理死循环
        // ctx.channel().writeAndFlush(responseMessage);
        // 这种是把消息传递到下一个handler处理
        // ctx.writeAndFlush(responseMessage);
    }
}

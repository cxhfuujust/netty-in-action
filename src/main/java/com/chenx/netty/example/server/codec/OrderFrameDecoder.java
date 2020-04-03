package com.chenx.netty.example.server.codec;

import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * 1.***FrameDecoder：解决tcp协议中的半包和沾包问题，如果不是tcp协议，当然就不一定要写了
 */
public class OrderFrameDecoder extends LengthFieldBasedFrameDecoder {
    //解决了没有半包和沾包的ByteBuf，然后转成业务能用的信息，就是requestMessage，所有就需要二次解码器了OrderProtocolDecoder
    public OrderFrameDecoder() {
        super(Integer.MAX_VALUE, 0, 2, 0, 2);
    }
}

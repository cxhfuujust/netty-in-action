package com.chenx.netty.example.server.codec;

import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

public class OrderFrameDecoder extends LengthFieldBasedFrameDecoder {
    //解决了没有半包和沾包的ByteBuf，然后转成业务能用的信息，就是requestMessage，所有就需要二次解码器了OrderProtocolDecoder
    public OrderFrameDecoder() {
        super(Integer.MAX_VALUE, 0, 2, 0, 2);
    }
}

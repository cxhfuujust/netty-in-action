package com.chenx.netty.example.client.codec;

import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * 第三步：处理服务器返回数据粘包/半包问题
 */
public class OrderFrameDecoder extends LengthFieldBasedFrameDecoder {
    //解决了没有半包和沾包的ByteBuf，然后转成业务能用的信息，就是requestMessage，所有就需要二次解码器了OrderProtocolDecoder
    public OrderFrameDecoder() {
        super(Integer.MAX_VALUE, 0, 2, 0, 2);
    }
}

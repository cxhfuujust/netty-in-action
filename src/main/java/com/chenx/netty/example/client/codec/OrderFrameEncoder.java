package com.chenx.netty.example.client.codec;

import io.netty.handler.codec.LengthFieldPrepender;

/**
 * 第二步：为了让服务器端能够处理粘包/半包问题，所以我要加上一个长度字段
 * 经过第二步之后，数据就发给了服务器端，服务器端作出处理给我们相应，响应回来之后，我们第一步就是要做解码，
 * 解码的第一步时粘包/半包的解码，要传到了OrderFrameDecoder
 */
public class OrderFrameEncoder extends LengthFieldPrepender {

    public OrderFrameEncoder() {
        super(2);
    }
}

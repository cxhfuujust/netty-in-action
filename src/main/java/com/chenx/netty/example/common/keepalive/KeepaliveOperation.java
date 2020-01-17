package com.chenx.netty.example.common.keepalive;

import com.chenx.netty.example.common.Operation;
import com.chenx.netty.example.common.OperationResult;
import lombok.Data;

@Data
public class KeepaliveOperation extends Operation {
    private long time;

    public KeepaliveOperation() {
        this.time = System.nanoTime();
    }

    @Override
    public OperationResult execute() {
        KeepaliveOperationResult orderResponse = new KeepaliveOperationResult(time);
        return orderResponse;
    }
}

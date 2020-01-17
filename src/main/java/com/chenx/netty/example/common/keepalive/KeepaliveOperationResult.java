package com.chenx.netty.example.common.keepalive;

import com.chenx.netty.example.common.OperationResult;
import lombok.Data;

@Data
public class KeepaliveOperationResult extends OperationResult {

    private final long time;

}

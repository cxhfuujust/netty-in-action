package com.chenx.netty.example.common.order;

import com.chenx.netty.example.common.OperationResult;
import lombok.Data;

@Data
public class OrderOperationResult extends OperationResult {
    private final int tableId;
    private final String dish;
    private final boolean complete;
}

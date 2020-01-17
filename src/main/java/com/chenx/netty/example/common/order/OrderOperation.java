package com.chenx.netty.example.common.order;

import com.chenx.netty.example.common.Operation;
import com.chenx.netty.example.common.OperationResult;
import lombok.Data;

@Data
public class OrderOperation extends Operation {

    private int tableId;
    private String dish;

    public OrderOperation(int tableId, String dish) {
        this.tableId = tableId;
        this.dish = dish;
    }

    @Override
    public OperationResult execute() {
        //do something
        OrderOperationResult orderResponse = new OrderOperationResult(tableId, dish, true);
        return orderResponse;
    }
}

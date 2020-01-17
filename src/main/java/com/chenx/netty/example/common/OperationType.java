package com.chenx.netty.example.common;

import com.chenx.netty.example.common.auth.AuthOperation;
import com.chenx.netty.example.common.auth.AuthOperationResult;
import com.chenx.netty.example.common.keepalive.KeepaliveOperation;
import com.chenx.netty.example.common.keepalive.KeepaliveOperationResult;
import com.chenx.netty.example.common.order.OrderOperation;
import com.chenx.netty.example.common.order.OrderOperationResult;

public enum  OperationType {
    AUTH(1, AuthOperation.class, AuthOperationResult.class),
    KEEPALIVE(2, KeepaliveOperation.class, KeepaliveOperationResult.class),
    ORDER(3, OrderOperation.class, OrderOperationResult.class);

    private int opCode;
    private Class<? extends Operation> operationClazz;
    private Class<? extends OperationResult> operationResultClazz;

    OperationType(int opCode, Class<? extends Operation> operationClazz, Class<? extends OperationResult> operationResultClazz) {
        this.opCode = opCode;
        this.operationClazz = operationClazz;
        this.operationResultClazz = operationResultClazz;
    }

    public static OperationType fromOpCode(int opcode) {
        OperationType[] values = OperationType.values();
        for (OperationType operationType : values) {
            if (opcode == operationType.getOpCode()) {
                return operationType;
            }
        }
        return null;
    }

    public static OperationType fromOperation(Operation operation) {
        OperationType[] values = OperationType.values();
        for (OperationType operationType : values) {
            if (operation.getClass().equals(operationType.getOperationClazz())) {
                return operationType;
            }
        }
        return null;
    }

    public int getOpCode() {
        return opCode;
    }

    public void setOpCode(int opCode) {
        this.opCode = opCode;
    }

    public Class<? extends Operation> getOperationClazz() {
        return operationClazz;
    }

    public void setOperationClazz(Class<? extends Operation> operationClazz) {
        this.operationClazz = operationClazz;
    }

    public Class<? extends OperationResult> getOperationResultClazz() {
        return operationResultClazz;
    }

    public void setOperationResultClazz(Class<? extends OperationResult> operationResultClazz) {
        this.operationResultClazz = operationResultClazz;
    }
}

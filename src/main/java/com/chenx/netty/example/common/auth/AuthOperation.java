package com.chenx.netty.example.common.auth;

import com.chenx.netty.example.common.Operation;
import com.chenx.netty.example.common.OperationResult;
import lombok.Data;

@Data
public class AuthOperation extends Operation {

    private final String userName;
    private final String password;

    @Override
    public OperationResult execute() {
        if ("admin".equals(this.userName)) {
            AuthOperationResult orderResponse = new AuthOperationResult(true);
            return orderResponse;
        }
        return new AuthOperationResult(true);
    }
}

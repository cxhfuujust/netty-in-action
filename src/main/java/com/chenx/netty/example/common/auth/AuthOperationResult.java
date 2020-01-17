package com.chenx.netty.example.common.auth;

import com.chenx.netty.example.common.OperationResult;
import lombok.Data;

@Data
public class AuthOperationResult extends OperationResult {

    private final boolean passAuth;

}

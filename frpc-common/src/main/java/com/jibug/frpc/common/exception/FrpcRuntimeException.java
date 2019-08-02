package com.jibug.frpc.common.exception;

/**
 * @author heyingcai
 */
public class FrpcRuntimeException extends RuntimeException{

    public FrpcRuntimeException() {
    }

    public FrpcRuntimeException(String message) {
        super(message);
    }

    public FrpcRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public FrpcRuntimeException(Throwable cause) {
        super(cause);
    }
}

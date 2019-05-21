package com.jibug.frpc.common.exception;

/**
 * @author heyingcai
 */
public class FrpRuntimeException extends RuntimeException{

    public FrpRuntimeException() {
    }

    public FrpRuntimeException(String message) {
        super(message);
    }

    public FrpRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public FrpRuntimeException(Throwable cause) {
        super(cause);
    }
}

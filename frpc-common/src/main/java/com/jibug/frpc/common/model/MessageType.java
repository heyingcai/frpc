package com.jibug.frpc.common.model;

/**
 * @author heyingcai
 */
public enum MessageType {
    REQUEST((byte) (1 << 2)),
    RESPONSE((byte) (1 << 4)),
    HEARTBEAT((byte) (0));

    private byte type;

    MessageType(byte type) {
        this.type = type;
    }

    public byte getType() {
        return type;
    }
}

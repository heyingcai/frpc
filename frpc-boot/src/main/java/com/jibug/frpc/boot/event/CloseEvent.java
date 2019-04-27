package com.jibug.frpc.boot.event;

import org.springframework.context.ApplicationEvent;

/**
 * @author heyingcai
 */
public class CloseEvent extends ApplicationEvent {

    private SignalEnum signal;

    public CloseEvent(Object source, SignalEnum signal) {
        super(source);
        this.signal = signal;
    }

    public SignalEnum getSignal() {
        return signal;
    }

    public void setSignal(SignalEnum signal) {
        this.signal = signal;
    }
}

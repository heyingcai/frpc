package com.jibug.frpc.common.interceptor;

import java.lang.reflect.Method;

/**
 * @author heyingcai
 * @date 2019-04-29 21:51
 */
public interface RpcInterceptor {

    /**
     * 前置处理
     *
     * @param target
     * @param method
     * @param args
     */
    void preHandle(Object target, Method method, Object[] args);

    /**
     * 过程处理
     *
     * @param target
     * @param method
     * @param args
     */
    void postHandle(Object target, Method method, Object[] args);

    /**
     * 后置处理
     *
     * @param target
     * @param method
     * @param args
     */
    void afterHandle(Object target, Method method, Object[] args);

}

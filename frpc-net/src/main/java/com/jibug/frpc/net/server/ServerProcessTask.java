package com.jibug.frpc.net.server;

import com.jibug.frpc.common.constant.ConfigConstants;
import com.jibug.frpc.common.model.FrpcRequest;
import com.jibug.frpc.common.model.FrpcRequestBody;
import com.jibug.frpc.common.model.FrpcResponse;
import com.jibug.frpc.common.util.SpringApplicationContext;
import io.netty.channel.ChannelHandlerContext;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author heyingcai
 */
public class ServerProcessTask implements Runnable {

    private FrpcRequest<FrpcRequestBody> request;

    private ChannelHandlerContext ctx;

    public ServerProcessTask(FrpcRequest request, ChannelHandlerContext ctx) {
        this.request = request;
        this.ctx = ctx;
    }

    private Object getServiceBean() {
        String serviceName = request.getRequestBody().getServiceName();
        Object serviceBean = null;
        if (StringUtils.isNotBlank(serviceName)) {
            serviceBean = SpringApplicationContext.getBean(serviceName);
        }
        String className = request.getRequestBody().getClassName();
        Class<?> aClass = null;
        try {
            aClass = Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        if (serviceBean == null) {
            serviceBean = SpringApplicationContext.getBean(aClass);
        }
        return serviceBean;
    }

    @Override
    public void run() {
        FrpcResponse response = new FrpcResponse();
        Object serviceBean = getServiceBean();

        Method method;
        Object result = null;
        try {
            method = serviceBean.getClass().getMethod(request.getRequestBody().getMethodName(), request.getRequestBody().getParameterTypes());
            result = method.invoke(serviceBean, request.getRequestBody().getParameters());
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        response.setRequestId(request.getRequestHeader().getRequestId());
        response.setStatus(ConfigConstants.SUCCESS_STATUS);
        response.setResult(result);
        ctx.writeAndFlush(response);
    }
}

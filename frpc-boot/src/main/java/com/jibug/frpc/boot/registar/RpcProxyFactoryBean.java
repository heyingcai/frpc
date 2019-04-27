package com.jibug.frpc.boot.registar;

import com.jibug.frpc.boot.event.CloseEvent;
import com.jibug.frpc.boot.event.SignalEnum;
import org.aopalliance.intercept.MethodInterceptor;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author heyingcai
 */
public class RpcProxyFactoryBean implements InitializingBean, FactoryBean<Object>, DisposableBean, BeanClassLoaderAware, ApplicationContextAware {

    private ApplicationContext applicationContext;
    private Class<?> interfaceName;
    private Object object;
    private ClassLoader classLoader;
    private MethodInterceptor interceptor;


    @Override
    public Object getObject() throws Exception {
        return object;
    }

    @Override
    public Class<?> getObjectType() {
        return interfaceName;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.addInterface(interfaceName);
        proxyFactory.addAdvice(interceptor);
        proxyFactory.setOptimize(false);
        object = proxyFactory.getProxy(classLoader);
    }

    @Override
    public void destroy() throws Exception {
        applicationContext.publishEvent(new CloseEvent(this, SignalEnum.CLOSED));
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public Class<?> getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(Class<?> interfaceName) {
        this.interfaceName = interfaceName;
    }

    public MethodInterceptor getInterceptor() {
        return interceptor;
    }

    public void setInterceptor(MethodInterceptor interceptor) {
        this.interceptor = interceptor;
    }
}

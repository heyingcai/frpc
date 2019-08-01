package com.jibug.frpc.common.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author heyingcai
 */
@Component
public class SpringApplicationContext implements ApplicationContextAware {

    private static ApplicationContext context;


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringApplicationContext.context = applicationContext;
    }

    public static Object getBean(String name) {
        return context.getBean(name);
    }

    public static Object getBean(Class clazz) {
        return context.getBean(clazz);
    }

    public static Map<String, Object> getBeansOfType(Class clazz) {
        return context.getBeansOfType(clazz);
    }

    public static boolean containsBean(String name) {
        return context.containsBean(name);
    }
}

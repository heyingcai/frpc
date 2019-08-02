package com.jibug.frpc.common.config;

import com.jibug.frpc.common.exception.FrpcRuntimeException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author heyingcai
 */
public class RpcServerConfig {

    private final static Map<String, Object> CONFIG = new ConcurrentHashMap<>();

    static {
        init();
    }

    private static void init() {
        InputStream inputStream = RegistryConfig.class.getClassLoader().getResourceAsStream("frpc-server.properties");
        Properties properties = new Properties();
        try {
            properties.load(inputStream);
            Iterator<Map.Entry<Object, Object>> iterator = properties.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<Object, Object> entry = iterator.next();
                CONFIG.put(entry.getKey().toString(), entry.getValue());
            }
        } catch (IOException e) {
            throw new FrpcRuntimeException("loading frpc-server config error", e);
        }
    }

    public static void put(String key, Object value) {
        Object oldValue = CONFIG.get(key);
        boolean canChange = oldValue == null ? value != null : !oldValue.equals(value);
        if (canChange) {
            CONFIG.put(key, value);
        }
    }

    public static boolean getBooleanValue(String key) {
        Object value = CONFIG.get(key);
        if (value == null) {
            throw new FrpcRuntimeException("The key of " + key + " not found from config");
        } else {
            return Boolean.valueOf(value.toString());
        }
    }

    public static int getIntValue(String key) {
        Object value = CONFIG.get(key);
        if (value == null) {
            throw new FrpcRuntimeException("The key of " + key + " not found from config");
        } else {
            return Integer.valueOf(value.toString());
        }
    }

    public static String getStringValue(String key) {
        String value = (String) CONFIG.get(key);
        if (value == null) {
            throw new FrpcRuntimeException("The key of " + key + " not found from config");
        } else {
            return value;
        }
    }
}

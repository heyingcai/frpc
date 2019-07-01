package com.jibug.frpc.common.cluster.registry.utils;

import com.jibug.frpc.common.config.ProviderConfig;
import com.jibug.frpc.common.config.ServerConfig;
import com.jibug.frpc.common.util.NetUtils;

import static com.jibug.frpc.common.constant.UrlParamKeyConstants.INTERFACE_KEY;

/**
 * @author heyingcai
 */
public class RegistryHelper {

    public static String convertConfigToUrl(ProviderConfig config) {
        ServerConfig serverConfig = config.getServerConfig();
        StringBuilder urlBuilder = new StringBuilder(200);
        String host = serverConfig.getHost();
        if (NetUtils.isInvalidLocalHost(host)) {
            host = NetUtils.getLocalAddress().getHostName();
        }
        urlBuilder.append(serverConfig.getProtocol()).append("://")
                .append(host).append(":")
                .append(serverConfig.getPort())
                .append(serverConfig.getContextPath()).append("?")
                .append(createKeyParams(INTERFACE_KEY, config.getInterfaceId()));
        return urlBuilder.toString();
    }

    public static String createProviderPath(String interfaceName) {
        return "/frpc/" + interfaceName + "/provider";
    }

    public static String createConsumerPath(String interfaceName) {
        return "/frpc/" + interfaceName + "/consumer";
    }

    public static String createKeyParams(String key, Object value) {
        if (value != null) {
            return "&" + key + "=" + value.toString();
        } else {
            return "";
        }
    }
}

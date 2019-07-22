package com.jibug.frpc.common.cluster.registry.utils;

import com.jibug.frpc.common.cluster.registry.ProviderInfo;
import com.jibug.frpc.common.config.ProviderConfig;
import com.jibug.frpc.common.config.ServerConfig;
import com.jibug.frpc.common.util.NetUtils;
import org.apache.curator.framework.recipes.cache.ChildData;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

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
                .append(serverConfig.getContextPath()).append("?version=1.0");
        return urlBuilder.toString();
    }

    public static List<ProviderInfo> convertUrlToProvider(String providerPath, List<ChildData> currentData) throws UnsupportedEncodingException {
        List<ProviderInfo> providerInfos = new ArrayList<>();
        if (currentData == null || currentData.isEmpty()) {
            return providerInfos;
        }
        for (ChildData childData : currentData) {
            providerInfos.add(convertUrlToProvider(providerPath, childData));
        }
        return providerInfos;
    }

    public static ProviderInfo convertUrlToProvider(String providerPath,
                                                    ChildData childData) throws UnsupportedEncodingException {
        String url = childData.getPath().substring(providerPath.length() + 1);
        url = URLDecoder.decode(url, "UTF-8");
        ProviderInfo providerInfo = convertToProviderInfo(url);
        return providerInfo;
    }

    static ProviderInfo convertToProviderInfo(String url) {
        ProviderInfo providerInfo = new ProviderInfo();
        int protocolIndex = url.indexOf("://");
        String remainUrl;
        if (protocolIndex > -1) {
            String protocol = url.substring(0, protocolIndex).toLowerCase();
            providerInfo.setProtocol(protocol);
            remainUrl = url.substring(protocolIndex + 3);
        } else {
            remainUrl = url;
        }

        int addressIndex = remainUrl.indexOf("/");
        String address;
        if (addressIndex > -1) {
            address = remainUrl.substring(0, addressIndex);
        } else {
            int itfIndex = remainUrl.indexOf('?');
            if (itfIndex > -1) {
                address = remainUrl.substring(0, itfIndex);
            } else {
                address = remainUrl;
            }
        }

        String[] ipAndPort = address.split(":", -1); // TODO 不支持ipv6
        providerInfo.setHost(ipAndPort[0]);
        if (ipAndPort.length > 1) {
            providerInfo.setPort(Integer.parseInt(ipAndPort[1]));
        }
        return providerInfo;
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

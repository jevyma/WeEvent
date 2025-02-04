package com.webank.weevent.broker.fisco.util;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.webank.weevent.BrokerApplication;
import com.webank.weevent.broker.fabric.sdk.FabricDelegate;
import com.webank.weevent.broker.fisco.constant.WeEventConstants;
import com.webank.weevent.sdk.WeEvent;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WeEventUtils {
    public static Map<String, String> getExtensions(Map<String, String> eventData) {
        Map<String, String> extensions = new HashMap<>();
        for (Map.Entry<String, String> extension : eventData.entrySet()) {
            if (extension.getKey().startsWith(WeEventConstants.EXTENSIONS_PREFIX_CHAR)) {
                extensions.put(extension.getKey(), extension.getValue());
            }
        }
        return extensions;
    }

    public static Map<String, String> getExtend(Map<String, List<String>> eventData) {
        Map<String, String> extensions = new HashMap<>();
        for (Map.Entry<String, List<String>> extension : eventData.entrySet()) {
            if (extension.getKey().startsWith(WeEventConstants.EXTENSIONS_PREFIX_CHAR)) {
                extensions.put(extension.getKey(), extension.getValue().get(0));
            }
        }
        return extensions;
    }

    public static String getClassPath() {
        URL url = WeEventUtils.class.getClassLoader().getResource("weevent.properties");
        return (new File(url.getPath())).getParentFile().getPath().concat(File.separator);
    }

    public static String getDefaultGroupId() {
        String groupId = null;
        if (WeEventConstants.FISCO.equals(BrokerApplication.weEventConfig.getBlockChainType())) {
            groupId = WeEvent.DEFAULT_GROUP_ID;
        } else if (WeEventConstants.FABRIC.equals(BrokerApplication.weEventConfig.getBlockChainType())){
            groupId = FabricDelegate.getChannelName();
        } else {
            log.error("error blockChain type!");
        }
        return groupId;
    }


}

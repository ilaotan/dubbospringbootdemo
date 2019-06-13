package com.ilaotan.util;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.springframework.context.ApplicationContext;

import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.spring.ReferenceBean;

public class ReferenceUtil {

    private static final ConcurrentMap<String, ReferenceBean<?>> referenceConfigs =
            new ConcurrentHashMap<String, ReferenceBean<?>>();

    private ReferenceUtil() {

    }

//    Object res = refer.getClass().getDeclaredMethod(functionName, paramTypes).invoke(refer, params);

//    Object res= refer.getClass().getDeclaredMethod(functionName).invoke(refer);


    /**
     * 获取Dubbo服务
     */
    public static Object refer(ApplicationContext applicationContext, String id, String interfaceName) {

        String group = "dubbo";
        String protocol = "dubbo";
        String version = "1.0.0";
        int timeOut = 60000;
        boolean check = false;
        int retries = 0;

        RegistryConfig registryConfig = (RegistryConfig) applicationContext.getBean("witparking-dubbo-registry");

        String key = "/" + group + "/" + interfaceName + ":";
        ReferenceBean<?> referenceConfig = referenceConfigs.get(key);
        if (referenceConfig == null) {
            referenceConfig = new ReferenceBean<>();
            referenceConfig.setId(id);
            referenceConfig.setInterface(interfaceName);
            referenceConfig.setApplicationContext(applicationContext);
            referenceConfig.setRegistry(registryConfig);
            referenceConfig.setVersion(version);
            referenceConfig.setProtocol(protocol);
            referenceConfig.setCheck(check);
            referenceConfig.setTimeout(timeOut);
            // RegistryConfig里已经配置group了
//            referenceConfig.setGroup(group);
            referenceConfig.setRetries(retries);
            try {
                referenceConfig.afterPropertiesSet();
            }
            catch (RuntimeException e) {
                throw e;
            }
            catch (Exception e) {
                throw new IllegalStateException(e.getMessage(), e);
            }
            referenceConfigs.putIfAbsent(key, referenceConfig);
            return referenceConfig.get();
        }
        return referenceConfig.get();
    }
}
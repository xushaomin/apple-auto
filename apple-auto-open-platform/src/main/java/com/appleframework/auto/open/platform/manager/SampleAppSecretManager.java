package com.appleframework.auto.open.platform.manager;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appleframework.rop.security.AppSecretManager;

public class SampleAppSecretManager implements AppSecretManager {
	
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    private static Map<String, String> appKeySecretMap = new HashMap<String, String>();

    static {
        appKeySecretMap.put("apple-auto", "open-platform");
    }

    @Override
    public String getSecret(String appKey) {
    	logger.info("use SampleAppSecretManager!");
        return appKeySecretMap.get(appKey);
    }

    @Override
    public boolean isValidAppKey(String appKey) {
        return getSecret(appKey) != null;
    }
}


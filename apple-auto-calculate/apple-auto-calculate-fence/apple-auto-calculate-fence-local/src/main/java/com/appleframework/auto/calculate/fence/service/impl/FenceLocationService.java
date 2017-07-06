package com.appleframework.auto.calculate.fence.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.appleframework.auto.calculate.fence.model.FenceLocation;

@Service("fenceLocationService")
public class FenceLocationService {

	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	private Map<String, Map<String, FenceLocation>> fenceLocationMap = new HashMap<>();

	public Map<String, FenceLocation> get(String key) {
		Map<String, Map<String, FenceLocation>> fenceLocationMap = getfenceLocationMap();
		return fenceLocationMap.get(key);
	}

	public void put(String key, Map<String, FenceLocation> map) {
		Map<String, Map<String, FenceLocation>> fenceLocationMap = getfenceLocationMap();
		fenceLocationMap.put(key, map);
	}

	public void delete(String key) {
		fenceLocationMap.remove(key);
	}

	private Map<String, Map<String, FenceLocation>> getfenceLocationMap() {
		//Map<String, Map<String, FenceLocation>> fenceLocationMap = hazelcastInstance.getMap("FENCE_LOCATION");
		return fenceLocationMap;
	}

}

package com.appleframework.auto.fence.calculate.service.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.appleframework.auto.fence.calculate.model.FenceLocation;
import com.appleframework.auto.fence.calculate.service.FenceLocationService;

public class FenceLocationServiceImpl implements FenceLocationService, Serializable {
	
	private static final long serialVersionUID = 1L;

	private static FenceLocationServiceImpl impl;
	
	public static void instance() {
		impl = new FenceLocationServiceImpl();
	}
	
	public static FenceLocationService getInstance() {
		if(null == impl) {
			impl = new FenceLocationServiceImpl();
		}
		return impl;
	}
	
	private Map<String, Map<String, FenceLocation>> fenceLocationMap = new HashMap<>();

	public Map<String, FenceLocation> get(String key) {
		Map<String, Map<String, FenceLocation>> fenceLocationMap = getfenceLocationMap();
		return fenceLocationMap.get(key);
	}

	public void update(String key, Map<String, FenceLocation> map) {
		Map<String, Map<String, FenceLocation>> fenceLocationMap = getfenceLocationMap();
		fenceLocationMap.put(key, map);
	}

	public void delete(String key) {
	}

	private Map<String, Map<String, FenceLocation>> getfenceLocationMap() {
		//Map<String, Map<String, FenceLocation>> fenceLocationMap = hazelcastInstance.getMap("FENCE_LOCATION");
		return fenceLocationMap;
	}

}

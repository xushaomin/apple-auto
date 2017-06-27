package com.appleframework.auto.fence.calculate.service.impl;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.appleframework.auto.fence.calculate.model.FenceLocation;
import com.appleframework.auto.fence.calculate.service.FenceLocationService;

public class FenceLocationServiceImpl implements FenceLocationService, Serializable {

	private static final long serialVersionUID = 1L;

	private Map<String, Map<String, FenceLocation>> fenceLocationMap = new ConcurrentHashMap<>();

	public Map<String, FenceLocation> get(String key) {
		Map<String, Map<String, FenceLocation>> fenceLocationMap = getfenceLocationMap();
		return fenceLocationMap.get(key);
	}

	public void update(String key, Map<String, FenceLocation> map) {
		Map<String, Map<String, FenceLocation>> fenceLocationMap = getfenceLocationMap();
		fenceLocationMap.put(key, map);
	}

	public void delete(String key) {
		fenceLocationMap.remove(key);
	}

	private Map<String, Map<String, FenceLocation>> getfenceLocationMap() {
		return fenceLocationMap;
	}

}

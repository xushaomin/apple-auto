package com.appleframework.auto.calculate.fence.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.appleframework.auto.calculate.fence.model.FenceLocation;
import com.appleframework.auto.calculate.fence.service.FenceLocationService;
import com.hazelcast.core.HazelcastInstance;

@Service("fenceLocationService")
public class FenceLocationServiceImpl implements FenceLocationService {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Resource
	private HazelcastInstance hazelcastInstance;
	
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
		hazelcastInstance.getMap(key).clear();
	}

	private Map<String, Map<String, FenceLocation>> getfenceLocationMap() {
		//Map<String, Map<String, FenceLocation>> fenceLocationMap = hazelcastInstance.getMap("FENCE_LOCATION");
		return fenceLocationMap;
	}

}

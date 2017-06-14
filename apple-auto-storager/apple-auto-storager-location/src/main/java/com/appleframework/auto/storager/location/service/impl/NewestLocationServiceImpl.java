package com.appleframework.auto.storager.location.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.appleframework.auto.storager.location.service.NewestLocationService;
import com.appleframework.bean.location.Location;
import com.hazelcast.core.HazelcastInstance;

@Service("newestLocationService")
public class NewestLocationServiceImpl implements NewestLocationService {

	@Resource
    private HazelcastInstance hazelcastInstance;

	@Override
	public void save(Location location) {
        Map<String, Location> newestLocationMap = hazelcastInstance.getMap("NEWEST_LOCATION");
        newestLocationMap.put(location.getAccount(), location);
	}

}
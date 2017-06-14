package com.appleframework.auto.storager.location.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.appleframework.auto.bean.location.Location;
import com.appleframework.auto.storager.location.service.NewestLocationService;
import com.hazelcast.core.HazelcastInstance;

@Service("newestLocationService")
public class NewestLocationServiceImpl implements NewestLocationService {

	protected final static Logger logger = Logger.getLogger(HbaseLocationServiceImpl.class);

	@Resource
    private HazelcastInstance hazelcastInstance;

	@Override
	public void save(Location location) {
        Map<String, Location> newestLocationMap = hazelcastInstance.getMap("NEWEST_LOCATION");
        String account = location.getAccount();
        if(logger.isDebugEnabled()) {
			logger.debug(account);
		}
        newestLocationMap.put(location.getAccount(), location);
	}

}
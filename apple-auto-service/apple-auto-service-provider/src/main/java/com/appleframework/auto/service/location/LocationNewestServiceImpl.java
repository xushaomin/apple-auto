package com.appleframework.auto.service.location;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.appleframework.auto.bean.location.Location;
import com.appleframework.auto.service.utils.PoiUtils;
import com.appleframework.exception.ServiceException;
import com.hazelcast.core.HazelcastInstance;

@Service("locationNewestService")
public class LocationNewestServiceImpl implements LocationNewestService {

	protected final static Logger logger = Logger.getLogger(LocationNewestServiceImpl.class);

	@Resource
	private HazelcastInstance hazelcastInstance;

	@Override
	public Location newest(String account, int mapType) throws ServiceException {
        Map<String, Location> newestLocationMap = hazelcastInstance.getMap("NEWEST_LOCATION");
        Location location = newestLocationMap.get(account);
        if(null != location) {
        	PoiUtils.fixPoi(location, mapType);
        }
		return location;
	}

}
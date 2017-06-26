package com.appleframework.auto.service.location;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.appleframework.auto.bean.location.Location;
import com.appleframework.auto.service.utils.PoiUtils;
import com.appleframework.cache.core.CacheManager;
import com.appleframework.exception.ServiceException;

@Service("locationNewestService")
public class LocationNewestServiceImpl implements LocationNewestService {

	protected final static Logger logger = Logger.getLogger(LocationNewestServiceImpl.class);

	@Resource
	private CacheManager cacheManager;

	@Override
	public Location newest(String account, int mapType) throws ServiceException {
        Location location = cacheManager.get(account, Location.class);
        if(null != location) {
        	PoiUtils.fixPoi(location, mapType);
        }
		return location;
	}

}
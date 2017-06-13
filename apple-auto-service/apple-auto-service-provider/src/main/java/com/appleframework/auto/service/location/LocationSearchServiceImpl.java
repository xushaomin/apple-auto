package com.appleframework.auto.service.location;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.appleframework.auto.service.location.key.LocationRowkey;
import com.appleframework.auto.service.utils.PoiUtils;
import com.appleframework.bean.location.Location;
import com.appleframework.data.hbase.client.SimpleHbaseClient;
import com.appleframework.exception.ServiceException;

@Service("locationSearchService")
public class LocationSearchServiceImpl implements LocationSearchService {

	protected final static Logger logger = Logger.getLogger(LocationSearchServiceImpl.class);

	@Resource
	private SimpleHbaseClient locationHbaseDao;

	@Override
	public List<Location> search(String account, long startTime, long endTime, int mapType) throws ServiceException {
		LocationRowkey startRowKey = LocationRowkey.create(account, startTime);
		LocationRowkey endRowKey = LocationRowkey.create(account, endTime);
		List<Location> list = locationHbaseDao.findObjectList(startRowKey, endRowKey, Location.class);
		for (Location location : list) {
			PoiUtils.fixPoi(location, mapType);
		}
		return list;
	}

}
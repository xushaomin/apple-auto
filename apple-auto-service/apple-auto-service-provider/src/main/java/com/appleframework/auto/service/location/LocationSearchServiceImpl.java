package com.appleframework.auto.service.location;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.appleframework.auto.service.location.key.LocationRowkey;
import com.appleframework.bean.location.Location;
import com.appleframework.data.hbase.client.SimpleHbaseClient;

@Service
public class LocationSearchServiceImpl implements LocationSearchService {

	protected final static Logger logger = Logger.getLogger(LocationSearchServiceImpl.class);

	@Resource
	private SimpleHbaseClient locationHbaseDao;

	@Override
	public List<Location> search(String account, long startTime, long endTime) {
		LocationRowkey startRowKey = LocationRowkey.create(account, startTime);
		LocationRowkey endRowKey = LocationRowkey.create(account, endTime);
		return locationHbaseDao.findObjectList(startRowKey, endRowKey, Location.class);
	}

}
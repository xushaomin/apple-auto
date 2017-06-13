package com.appleframework.auto.storager.location.service.impl;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.appleframework.auto.storager.location.key.LocationRowkey;
import com.appleframework.auto.storager.location.service.HbaseLocationService;
import com.appleframework.bean.location.Location;
import com.appleframework.bean.location.LocationProto.Model;
import com.appleframework.data.hbase.client.RowKey;
import com.appleframework.data.hbase.client.SimpleHbaseClient;

@Service("hbaseLocationService")
public class HbaseLocationServiceImpl implements HbaseLocationService {

	protected final static Logger logger = Logger.getLogger(HbaseLocationServiceImpl.class);

	@Resource
	private SimpleHbaseClient locationHbaseDao;

	@Override
	public void save(Location location) {
		RowKey rowKey = LocationRowkey.create(location);
		if(logger.isDebugEnabled()) {
			logger.debug(rowKey);
		}
		locationHbaseDao.putObject(rowKey, location);
	}

	@Override
	public void save(Model model) {
		Location builder = new Location();
		builder.setAccount(model.getAccount());
		builder.setLatitude(model.getLatitude());
		builder.setLongitude(model.getLongitude());
		builder.setAltitude(model.getAltitude());
		builder.setSpeed(model.getSpeed());
		builder.setDirection(model.getDirection());
		builder.setTime(model.getTime());
		this.save(builder);
	}

}
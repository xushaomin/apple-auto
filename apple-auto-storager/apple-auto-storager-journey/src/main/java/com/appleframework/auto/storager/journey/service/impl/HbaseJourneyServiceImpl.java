package com.appleframework.auto.storager.journey.service.impl;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.appleframework.auto.bean.location.Journey;
import com.appleframework.auto.storager.journey.key.JourneyRowkey;
import com.appleframework.auto.storager.journey.service.HbaseJourneyService;
import com.appleframework.data.hbase.client.RowKey;
import com.appleframework.data.hbase.client.SimpleHbaseAdminClient;
import com.appleframework.data.hbase.client.SimpleHbaseClient;

@Service("hbaseJourneyService")
public class HbaseJourneyServiceImpl implements HbaseJourneyService {

	protected final static Logger logger = Logger.getLogger(HbaseJourneyServiceImpl.class);

	@Resource
	private SimpleHbaseClient journeyHbaseDao;
	
	@Resource
	private SimpleHbaseAdminClient hbaseAdminClient;

	@Override
	public void save(Journey journey) {
		RowKey rowKey = JourneyRowkey.create(journey);
		if(logger.isDebugEnabled()) {
			logger.debug(rowKey);
		}
		journeyHbaseDao.putObject(rowKey, journey);
	}
	
	public void createTable() {
		journeyHbaseDao.getHbaseTableConfig().getHbaseTableSchema().
	}

}
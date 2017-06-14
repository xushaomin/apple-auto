package com.appleframework.auto.storager.location.consumer;

import javax.annotation.Resource;

import com.appleframework.auto.bean.location.Location;
import com.appleframework.auto.storager.location.service.HbaseLocationService;
import com.appleframework.auto.storager.location.service.NewestLocationService;
import com.appleframework.jms.kafka.consumer.ObjectMessageConsumer;

public class LocationConsumer extends ObjectMessageConsumer {

	@Resource
	private HbaseLocationService hbaseLocationService;

	@Resource
	private NewestLocationService newestLocationService;

	@Override
	public void processMessage(Object message) {
		try {
			if (message instanceof Location) {
				Location location = (Location) message;
				hbaseLocationService.save(location);
				newestLocationService.save(location);
			}
		} catch (Exception e) {
		}
	}

}

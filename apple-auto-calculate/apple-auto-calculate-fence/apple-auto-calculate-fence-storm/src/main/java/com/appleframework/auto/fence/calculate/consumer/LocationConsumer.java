package com.appleframework.auto.fence.calculate.consumer;

import javax.annotation.Resource;

import com.appleframework.auto.bean.location.Location;
import com.appleframework.auto.fence.calculate.service.FenceCalculateService;
import com.appleframework.jms.kafka.consumer.ObjectMessageConsumer;

public class LocationConsumer extends ObjectMessageConsumer {
	
	@Resource
	private FenceCalculateService fenceCalculateService;

	@Override
	public void processMessage(Object message) {
		try {
			if (message instanceof Location) {
				Location location = (Location) message;
				fenceCalculateService.calculate(location);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return;
	}
	
}

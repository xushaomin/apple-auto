package com.appleframework.auto.calculate.fence.consumer;

import javax.annotation.Resource;

import com.appleframework.auto.bean.location.Location;
import com.appleframework.auto.calculate.fence.service.impl.FenceCalculateService;
import com.appleframework.jms.kafka.consumer.ObjectMessageConsumer;

public class LocationConsumer extends ObjectMessageConsumer {
		
	@Resource
	private FenceCalculateService fenceCalculateService;

	@Override
	public void processMessage(Object message) {
		try {
			if (message instanceof Location) {
				Location location = (Location) message;
				//long t = System.currentTimeMillis();
				fenceCalculateService.calculate(location);
				//System.out.println(System.currentTimeMillis() - t);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return;
	}
	
}

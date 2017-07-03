package com.appleframework.auto.calculate.fence.consumer;

import javax.annotation.Resource;

import com.appleframework.auto.bean.location.Location;
import com.appleframework.auto.calculate.fence.service.impl.CalculateCircleService;
import com.appleframework.auto.calculate.fence.service.impl.CalculateRectangleService;
import com.appleframework.jms.kafka.consumer.ObjectMessageConsumer;

public class LocationConsumer extends ObjectMessageConsumer {
	
	@Resource
	private CalculateCircleService calculateCircleService;
	
	@Resource
	private CalculateRectangleService calculateRectangleService;

	@Override
	public void processMessage(Object message) {
		try {
			if (message instanceof Location) {
				Location location = (Location) message;
				calculateCircleService.calculate(location);
				calculateRectangleService.calculate(location);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return;
	}
	
}

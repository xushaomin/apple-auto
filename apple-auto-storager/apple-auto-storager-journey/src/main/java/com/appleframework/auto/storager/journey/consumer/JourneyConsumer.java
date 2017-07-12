package com.appleframework.auto.storager.journey.consumer;

import javax.annotation.Resource;

import com.appleframework.auto.bean.location.Journey;
import com.appleframework.auto.storager.journey.service.HbaseJourneyService;
import com.appleframework.jms.kafka.consumer.ObjectMessageConsumer;

public class JourneyConsumer extends ObjectMessageConsumer {

	@Resource
	private HbaseJourneyService hbaseJourneyService;

	@Override
	public void processMessage(Object message) {
		try {
			if (message instanceof Journey) {
				Journey journey = (Journey) message;
				hbaseJourneyService.save(journey);
			}
		} catch (Exception e) {
		}
	}

}

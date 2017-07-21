package com.appleframework.auto.calculate.notify.consumer;

import javax.annotation.Resource;

import com.appleframework.auto.bean.fence.FenceResult;
import com.appleframework.auto.calculate.notify.service.impl.NotifyCimService;
import com.appleframework.jms.kafka.consumer.ObjectMessageConsumer;

public class FenceResultConsumer extends ObjectMessageConsumer {

	@Resource
	private NotifyCimService notifyCimService;
	
	@Override
	public void processMessage(Object message) {
		try {
			if (message instanceof FenceResult) {
				FenceResult result = (FenceResult) message;
				notifyCimService.notify(result);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return;
	}

}
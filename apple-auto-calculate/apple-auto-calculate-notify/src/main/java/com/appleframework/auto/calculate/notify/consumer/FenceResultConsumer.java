package com.appleframework.auto.calculate.notify.consumer;

import com.appleframework.auto.bean.fence.FenceResult;
import com.appleframework.auto.bean.location.Location;
import com.appleframework.jms.kafka.consumer.ObjectMessageConsumer;

public class FenceResultConsumer extends ObjectMessageConsumer {

	@Override
	public void processMessage(Object message) {
		try {
			if (message instanceof Location) {
				FenceResult result = (FenceResult) message;
				System.out.println(result);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return;
	}

}
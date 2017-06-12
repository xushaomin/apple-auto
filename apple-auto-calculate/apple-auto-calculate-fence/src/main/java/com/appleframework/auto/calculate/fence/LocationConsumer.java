package com.appleframework.auto.calculate.fence;

import com.appleframework.jms.kafka.consumer.BytesMessageConsumer;

public class LocationConsumer extends BytesMessageConsumer {

	@Override
	public void processMessage(byte[] message) {
		System.out.println(message);
	}
	
}

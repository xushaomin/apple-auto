package com.appleframework.auto.calculate.fence.consumer;

import javax.annotation.Resource;

import com.appleframework.auto.bean.location.LocationProto;
import com.appleframework.auto.calculate.fence.service.FenceCalculateService;
import com.appleframework.jms.kafka.consumer.BytesMessageConsumer;
import com.google.protobuf.InvalidProtocolBufferException;

public class LocationConsumer extends BytesMessageConsumer {
	
	@Resource
	private FenceCalculateService fenceCalculateService;

	@Override
	public void processMessage(byte[] message) {
		try {
			LocationProto.Model model = LocationProto.Model.parseFrom(message);
			fenceCalculateService.calculate(model);
		} catch (InvalidProtocolBufferException e) {
			e.printStackTrace();
		}
		return;
	}
	
}

package com.appleframework.auto.storager.location.consumer;

import javax.annotation.Resource;

import com.appleframework.auto.storager.location.service.HbaseLocationService;
import com.appleframework.bean.location.LocationProto;
import com.appleframework.jms.kafka.consumer.BytesMessageConsumer;
import com.google.protobuf.InvalidProtocolBufferException;

public class LocationConsumer extends BytesMessageConsumer {

	@Resource
	private HbaseLocationService hbaseLocationService;

	@Override
	public void processMessage(byte[] message) {
		try {
			LocationProto.Model location = LocationProto.Model.parseFrom(message);
			hbaseLocationService.save(location);
		} catch (InvalidProtocolBufferException e) {
		}

	}

}

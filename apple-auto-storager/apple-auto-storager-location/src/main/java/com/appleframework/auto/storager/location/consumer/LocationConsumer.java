package com.appleframework.auto.storager.location.consumer;

import javax.annotation.Resource;

import com.appleframework.auto.bean.location.Location;
import com.appleframework.auto.bean.location.LocationProto;
import com.appleframework.auto.storager.location.service.HbaseLocationService;
import com.appleframework.auto.storager.location.service.NewestLocationService;
import com.appleframework.jms.kafka.consumer.BytesMessageConsumer;
import com.google.protobuf.InvalidProtocolBufferException;

public class LocationConsumer extends BytesMessageConsumer {

	@Resource
	private HbaseLocationService hbaseLocationService;
	
	@Resource
	private NewestLocationService newestLocationService;

	@Override
	public void processMessage(byte[] message) {
		try {
			LocationProto.Model model = LocationProto.Model.parseFrom(message);
			Location location = Location.builder(model);
			hbaseLocationService.save(location);
			newestLocationService.save(location);
		} catch (InvalidProtocolBufferException e) {
		}

	}

}

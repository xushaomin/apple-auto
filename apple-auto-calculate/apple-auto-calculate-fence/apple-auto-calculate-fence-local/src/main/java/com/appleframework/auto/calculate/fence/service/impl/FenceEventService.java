package com.appleframework.auto.calculate.fence.service.impl;

import java.util.Set;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.appleframework.auto.bean.location.Location;
import com.appleframework.auto.calculate.fence.model.FenceEvent;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;

@Service("fenceEventService")
public class FenceEventService {

	protected final static Logger logger = Logger.getLogger(FenceEventService.class);
	
	@Resource
	private Disruptor<FenceEvent> disruptor;
	
	public void publishEvent(Location location, Set<String> fenceIdSet) {
		RingBuffer<FenceEvent> ringBuffer = disruptor.getRingBuffer();
		long sequence = ringBuffer.next(); // 申请位置
		try {
			FenceEvent event = ringBuffer.get(sequence);
			event.setFenceEvent(location, fenceIdSet); // 放置数据
		} finally {
			ringBuffer.publish(sequence); // 提交，如果不提交完成事件会一直阻塞
		}
	}

}

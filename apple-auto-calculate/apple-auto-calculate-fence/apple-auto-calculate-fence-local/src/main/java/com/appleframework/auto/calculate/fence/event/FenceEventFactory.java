package com.appleframework.auto.calculate.fence.event;

import com.appleframework.auto.calculate.fence.model.FenceEvent;
import com.lmax.disruptor.EventFactory;

public class FenceEventFactory implements EventFactory<FenceEvent> {
	
	public FenceEvent newInstance() {
		return new FenceEvent();
	}
	
}
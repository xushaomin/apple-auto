package com.appleframework.auto.calculate.fence.event;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.FactoryBean;

import com.appleframework.auto.calculate.fence.model.FenceEvent;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.LiteBlockingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

public class DisruptorFactoryBean implements FactoryBean<Disruptor<FenceEvent>> {

	private EventHandler<FenceEvent> eventHandler;

	private Disruptor<FenceEvent> disruptor;

	@SuppressWarnings({ "deprecation", "unchecked" })
	@Override
	public Disruptor<FenceEvent> getObject() throws Exception {
		Executor executor = Executors.newCachedThreadPool();
		int bufferSize = 1024 * 1024;
		EventFactory<FenceEvent> eventFactory = new FenceEventFactory();
		disruptor = new Disruptor<>(eventFactory, bufferSize, executor, ProducerType.SINGLE,
				new LiteBlockingWaitStrategy());
		disruptor.handleEventsWith(eventHandler);
		disruptor.start();
		return disruptor;

	}

	@SuppressWarnings("rawtypes")
	@Override
	public Class<Disruptor> getObjectType() {
		return Disruptor.class;
	}

	@Override
	public boolean isSingleton() {
		return false;
	}

	public void setEventHandler(EventHandler<FenceEvent> eventHandler) {
		this.eventHandler = eventHandler;
	}

	public void destroy() {
		disruptor.shutdown();
	}

}

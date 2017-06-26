package com.appleframework.auto.calculate.fence.consumer;

import javax.annotation.Resource;

import com.appleframework.auto.bean.fence.SyncOperate;
import com.appleframework.auto.calculate.fence.service.FenceInfoService;
import com.appleframework.jms.jedis.consumer.TopicObjectMessageConsumer;

public class FenceConsumer extends TopicObjectMessageConsumer<Object> {

	@Resource
	private FenceInfoService fenceInfoService;

	@Override
	public void processMessage(Object message) {
		if (message instanceof SyncOperate) {
			SyncOperate operate = (SyncOperate) message;
			if (operate.getOperate() == 1) {
				fenceInfoService.create(operate.getNewFence());
			} else if (operate.getOperate() == 2) {
				fenceInfoService.update(operate.getOldFence(), operate.getNewFence());
			} else {
				fenceInfoService.delete(operate.getOldFence());
			}
		}
	}

}

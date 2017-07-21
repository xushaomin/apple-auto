package com.appleframework.auto.calculate.fence.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.appleframework.auto.bean.fence.Fence;
import com.appleframework.auto.bean.fence.FenceResult;
import com.appleframework.auto.bean.location.Location;
import com.appleframework.auto.calculate.fence.service.FenceInfoService;
import com.appleframework.config.core.PropertyConfigurer;
import com.appleframework.jms.core.producer.MessageProducer3;

@Service("fenceNotifyService")
public class FenceNotifyService {

	protected final static Logger logger = Logger.getLogger(FenceNotifyService.class);

	@Resource
	private FenceInfoService fenceInfoService;

	@Resource
	private MessageProducer3 messageProducer3;

	private DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public void notify(String account, Location location, String fenceId, Integer type) {
		Fence fence = fenceInfoService.get(fenceId);
		if (type == 1) {
			logger.warn("\t进入围栏:" + fence.getId() + ":" + fence.getName() + ":" + fence.getType() + ":" + account
					+ "\t进入时间:" + format.format(new Date(location.getTime())) + "\tlat:" + location.getLatitude()
					+ " \tlng:" + location.getLongitude());

		} else {
			logger.warn("\t退出围栏:" + fence.getId() + ":" + fence.getName() + ":" + fence.getType() + ":" + account
					+ "\t退出时间:" + format.format(new Date(location.getTime())) + "\tlat:" + location.getLatitude()
					+ " \tlng:" + location.getLongitude());
		}
		try {
			FenceResult result = new FenceResult();
			result.setAccount(account);
			result.setFenceId(fenceId);
			result.setLocation(location);
			result.setType(type);
			this.send(result);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

	private void send(FenceResult result) {
		String topic = PropertyConfigurer.getString("producer.topic");
		messageProducer3.sendObject(topic, result.getAccount(), result);
	}

}

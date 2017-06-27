package com.appleframework.auto.fence.calculate;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.storm.topology.base.BaseRichBolt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appleframework.auto.bean.location.Location;
import com.appleframework.auto.fence.calculate.model.Message;
import com.appleframework.boot.utils.HttpUtils;
import com.taobao.diamond.utils.JSONUtils;

/**
 * 简单的按照空格进行切分后，发射到下一阶段bolt
 */
public abstract class BaseFenceNotifyBolt extends BaseRichBolt {

	private static final Logger logger = LoggerFactory.getLogger(BaseFenceNotifyBolt.class);

	private static final long serialVersionUID = 1L;

	protected Properties props;

	public void notify(String account, Location location, String fenceId, Integer type) {
		String url = props.getProperty("notify.url");
		logger.info(url);

		Message message = new Message();
		message.setAction(2);

		if (type == 1)
			message.setContent("进入围栏：" + fenceId);
		else
			message.setContent("离开围栏：" + fenceId);

		message.setSender("system");
		message.setReceiver(account);

		Map<String, String> params = new HashMap<>();
		try {
			params.put("message", JSONUtils.serializeObject(message));
			HttpUtils.post(url, params);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

package com.appleframework.auto.fence.calculate;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.storm.topology.base.BaseRichBolt;

import com.appleframework.auto.bean.location.Location;
import com.appleframework.boot.utils.HttpUtils;

/**
 * 简单的按照空格进行切分后，发射到下一阶段bolt
 */
public abstract class BaseFenceNotifyBolt extends BaseRichBolt {

	//private static final Logger logger = LoggerFactory.getLogger(BaseFenceNotifyBolt.class);

	private static final long serialVersionUID = 1L;

	protected Properties props;

	public void notify(String account, Location location, String fenceId, Integer type) {
		String url = props.getProperty("notify.url");
		String content = null;

		if (type == 1)
			content = "进入围栏：" + fenceId;
		else
			content = "离开围栏：" + fenceId;

		Map<String, String> params = new HashMap<>();
		try {
			params.put("content", content);
			params.put("action", "2");
			params.put("sender", "system");
			params.put("receiver", account);
			HttpUtils.post(url, params);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

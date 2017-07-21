package com.appleframework.auto.calculate.notify.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.appleframework.auto.bean.fence.Fence;
import com.appleframework.auto.bean.fence.FenceResult;
import com.appleframework.boot.utils.HttpUtils;
import com.appleframework.config.core.PropertyConfigurer;

@Service("notifyCimService")
public class NotifyCimService {
	
	@Resource
	private FenceCacheService fenceCacheService;

	protected final static Logger logger = Logger.getLogger(NotifyCimService.class);

	public void notify(FenceResult result) {
		String account = result.getAccount();
		String fenceId = result.getFenceId();
		Integer type = result.getType();
		String url = PropertyConfigurer.getString("notify.url");
		String content = null;
		Fence fence = fenceCacheService.get(fenceId);
		if(null == fence)
			return;
		if (type == 1)
			content = "进入围栏：" + fenceId + "(" + fence.getName() + ")";
		else
			content = "离开围栏：" + fenceId + "(" + fence.getName() + ")";

		Map<String, String> params = new HashMap<>();
		try {
			params.put("content", content);
			params.put("action", "2");
			params.put("sender", "system");
			params.put("receiver", account);
			HttpUtils.post(url, params);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

}

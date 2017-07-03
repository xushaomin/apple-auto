package com.appleframework.auto.calculate.fence.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.appleframework.auto.bean.fence.Fence;
import com.appleframework.auto.bean.location.Location;
import com.appleframework.auto.calculate.fence.service.FenceInfoService;
import com.appleframework.boot.utils.HttpUtils;
import com.appleframework.config.core.PropertyConfigurer;

@Service("fenceNotifyService")
public class FenceNotifyService {

	protected final static Logger logger = Logger.getLogger(FenceNotifyService.class);
	
	@Resource
	private FenceInfoService fenceInfoService;
	
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

		Boolean isOpen = PropertyConfigurer.getBoolean("notify.open", false);
		if(!isOpen)
			return;
		String url = PropertyConfigurer.getString("notify.url");

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
			//e.printStackTrace();
		}
	}

}

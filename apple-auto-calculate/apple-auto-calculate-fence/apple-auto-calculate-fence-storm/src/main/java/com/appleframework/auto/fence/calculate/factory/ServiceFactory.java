package com.appleframework.auto.fence.calculate.factory;

import com.appleframework.auto.fence.calculate.service.FenceCalculateService;
import com.appleframework.auto.fence.calculate.service.FenceInfoService;
import com.appleframework.auto.fence.calculate.service.FenceLocationService;
import com.appleframework.auto.fence.calculate.service.impl.FenceCalculateServiceImpl;
import com.appleframework.auto.fence.calculate.service.impl.FenceInfoServiceImpl;
import com.appleframework.auto.fence.calculate.service.impl.FenceLocationServiceImpl;

public class ServiceFactory {

	private static FenceLocationService fenceLocationService = new FenceLocationServiceImpl();
	private static FenceInfoService fenceInfoService = new FenceInfoServiceImpl();
	private static FenceCalculateService fenceCalculateService = new FenceCalculateServiceImpl();
	
	private static boolean isInited = false;

	public static void init() {
		if(!isInited) {
			System.out.println("-----------------2222222222222222------");
			fenceInfoService.setPoolFactory(RedisFactory.getInstance());
			fenceInfoService.init();
			fenceCalculateService.setFenceInfoService(fenceInfoService);
			fenceCalculateService.setFenceLocationService(fenceLocationService);
			isInited = true;
		}
	}

	public static FenceLocationService getFenceLocationService() {
		return fenceLocationService;
	}
	
	public static FenceInfoService getFenceInfoService() {
		return fenceInfoService;
	}
	
	public static FenceCalculateService getFenceCalculateService() {
		return fenceCalculateService;
	}
}

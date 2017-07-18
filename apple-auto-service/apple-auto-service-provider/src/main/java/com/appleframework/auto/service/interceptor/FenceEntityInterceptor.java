package com.appleframework.auto.service.interceptor;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import com.appleframework.auto.entity.fence.FenceEntityWithBLOBs;
import com.appleframework.auto.service.fence.FenceEntityService;
import com.appleframework.auto.service.fence.FenceInfoService;

@Component
@Aspect
public class FenceEntityInterceptor {

	private static Logger logger = Logger.getLogger(FenceEntityInterceptor.class.getName());

	@Resource
	private FenceInfoService fenceInfoService;

	@Resource
	private FenceEntityService fenceEntityService;

	//@AfterReturning(value = "execution(* com.appleframework.auto.service.fence.impl.FenceEntityServiceImpl.save(..))", argNames = "rtv", returning = "rtv")
	public void save(JoinPoint jp, final Object rtv) {
		logger.error("fence insert success...");
		Integer id = (Integer) rtv;
		FenceEntityWithBLOBs entity = fenceEntityService.get(id);
		fenceInfoService.create(entity);
	}

}
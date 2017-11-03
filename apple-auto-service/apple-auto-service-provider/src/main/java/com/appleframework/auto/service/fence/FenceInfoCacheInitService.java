package com.appleframework.auto.service.fence;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.appleframework.auto.entity.fence.FenceEntityWithBLOBs;

@Service("fenceInfoCacheInitService")
public class FenceInfoCacheInitService {

	protected final static Logger logger = Logger.getLogger(FenceInfoCacheInitService.class);
	
	@Resource
	private FenceCacheService fenceCacheService;
	
	@Resource
	private FenceInfoService fenceInfoService;
	
	@Resource
	private FenceEntityService fenceEntityService;

	@PostConstruct
	public void init() {
		try {
			fenceCacheService.clear();
			List<FenceEntityWithBLOBs> list = fenceEntityService.findAll();
			for (FenceEntityWithBLOBs fenceEntityWithBLOBs : list) {
				fenceInfoService.create(fenceEntityWithBLOBs);
			}
		} catch (Exception e) {
			logger.error(e);
		}

	}

}
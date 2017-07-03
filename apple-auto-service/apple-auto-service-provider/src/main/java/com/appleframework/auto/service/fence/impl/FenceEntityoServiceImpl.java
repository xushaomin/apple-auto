package com.appleframework.auto.service.fence.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.appleframework.auto.entity.fence.FenceEntityWithBLOBs;
import com.appleframework.auto.service.dao.FenceEntityDao;
import com.appleframework.auto.service.fence.FenceEntityService;

@Service("fenceEntityService")
public class FenceEntityoServiceImpl implements FenceEntityService {

	protected final static Logger logger = Logger.getLogger(FenceEntityoServiceImpl.class);

	@Resource
	private FenceEntityDao fenceEntityDao;

	@Override
	public List<FenceEntityWithBLOBs> findAll() {
		return fenceEntityDao.findAll();
	}

}
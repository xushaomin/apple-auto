package com.appleframework.auto.service.fence.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.appleframework.auto.entity.fence.FenceEntityWithBLOBs;
import com.appleframework.auto.model.fence.FenceSo;
import com.appleframework.auto.service.dao.FenceEntityDao;
import com.appleframework.auto.service.fence.FenceEntityService;
import com.appleframework.model.page.Pagination;

@Service("fenceEntityService")
public class FenceEntityoServiceImpl implements FenceEntityService {

	protected final static Logger logger = Logger.getLogger(FenceEntityoServiceImpl.class);

	@Resource
	private FenceEntityDao fenceEntityDao;

	@Override
	public List<FenceEntityWithBLOBs> findAll() {
		return fenceEntityDao.findAll();
	}

	@Override
	public Pagination findPage(Pagination page, FenceSo so) {
		page.setList(fenceEntityDao.findPage(page, so));
		return page;
	}

}
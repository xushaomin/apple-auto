package com.appleframework.auto.service.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.appleframework.auto.entity.fence.FenceEntity;
import com.appleframework.auto.entity.fence.FenceEntityWithBLOBs;
import com.appleframework.auto.model.fence.FenceSo;
import com.appleframework.auto.service.mapper.FenceEntityMapper;
import com.appleframework.auto.service.mapper.FenceExtendMapper;
import com.appleframework.model.page.Pagination;

@Repository("fenceEntityDao")
public class FenceEntityDao {

	@Resource
	private FenceEntityMapper fenceEntityMapper;
	
	@Resource
	private FenceExtendMapper fenceExtendMapper;
	
	public List<FenceEntityWithBLOBs> findAll() {
		return fenceExtendMapper.selectAll();
	}
	
	public List<FenceEntity> findPage(Pagination page, FenceSo so) {
		return fenceExtendMapper.selectPage(page, so);
	}
}

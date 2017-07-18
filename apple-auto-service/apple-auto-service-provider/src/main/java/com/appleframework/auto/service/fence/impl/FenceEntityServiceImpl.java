package com.appleframework.auto.service.fence.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.appleframework.auto.bean.fence.Point;
import com.appleframework.auto.entity.fence.FenceEntityWithBLOBs;
import com.appleframework.auto.model.fence.FenceSo;
import com.appleframework.auto.service.dao.FenceEntityDao;
import com.appleframework.auto.service.fence.FenceEntityService;
import com.appleframework.auto.service.fence.FenceInfoService;
import com.appleframework.auto.service.utils.PoiUtils;
import com.appleframework.exception.AppleException;
import com.appleframework.model.Search;
import com.appleframework.model.page.Pagination;

@Service("fenceEntityService")
public class FenceEntityServiceImpl implements FenceEntityService {

	protected final static Logger logger = Logger.getLogger(FenceEntityServiceImpl.class);

	@Resource
	private FenceEntityDao fenceEntityDao;
	
	@Resource
	private FenceInfoService fenceInfoService;

	@Override
	public List<FenceEntityWithBLOBs> findAll() {
		return fenceEntityDao.findAll();
	}

	@Override
	public Pagination findPage(Pagination page, FenceSo so, Search se) {
		page.setList(fenceEntityDao.findPage(page, so, se));
		return page;
	}
	
	@Override
	public FenceEntityWithBLOBs get(Integer id) {
		return fenceEntityDao.get(id);
	}
	
	@Override
	public Integer save(FenceEntityWithBLOBs entity) throws AppleException {
		entity.setIsDelete(false);
		entity.setIsEnable(true);
		entity.setCreateTime(new Date());
		if(null == entity.getColor())
			entity.setColor("#fc261d");
		String parameter = entity.getParameter();
		String latitudes = "";
		String longitudes = "";
		String[] parameterArray = parameter.split("[|]");
		for (int i = 0; i < parameterArray.length; i++) {
			String longlat = parameterArray[i];
			Point point = new Point(longlat);
			PoiUtils.fixPoi(point, 3);
			if (i == parameterArray.length - 1) {
				latitudes += point.getLatitude() + "";
				longitudes += point.getLongitude() + "";
			} else {
				latitudes += point.getLatitude() + ",";
				longitudes += point.getLongitude() + ",";
			}
		}
		entity.setLatitudes(latitudes);
		entity.setLongitudes(longitudes);
		entity.setClientId(1);
		fenceEntityDao.save(entity);
		fenceInfoService.create(entity);
		return entity.getId();
	}

}
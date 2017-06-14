package com.appleframework.auto.service.location;

import com.appleframework.auto.bean.location.Location;
import com.appleframework.exception.ServiceException;

/**
 * 轨迹搜索
 *
 */
public interface LocationNewestService {
	
	public Location newest(String account, int mapType) throws ServiceException;
	
}
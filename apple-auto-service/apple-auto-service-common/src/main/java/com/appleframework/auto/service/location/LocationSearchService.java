package com.appleframework.auto.service.location;

import java.util.List;

import com.appleframework.bean.location.Location;

/**
 * 轨迹搜索
 *
 */
public interface LocationSearchService {
	
	public List<Location> search(String account, long startTime, long endTime);
	
}

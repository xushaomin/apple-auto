package com.appleframework.auto.service.fence;


import com.appleframework.exception.ServiceException;

/**
 * 轨迹搜索
 *
 */
public interface FenceInfoService {
	
	public void create(Fence fence) throws ServiceException;
	
}
